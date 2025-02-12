package reviewme.global.authorization;

import static reviewme.global.authorization.ResourceAuthorizationUtils.getCurrentSession;
import static reviewme.global.authorization.ResourceAuthorizationUtils.getTarget;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reviewme.auth.domain.GitHubMember;
import reviewme.global.authorization.exception.UnauthorizedReviewAccessException;
import reviewme.global.session.SessionManager;
import reviewme.review.domain.Review;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.exception.ReviewNotFoundException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Aspect
@Component
@RequiredArgsConstructor
public class ReviewAuthorizationAspect {

    private final SessionManager sessionManager;
    private final ReviewRepository reviewRepository;
    private final ReviewGroupRepository reviewGroupRepository;

    @Transactional
    @Around("@annotation(requireReviewAccess)")
    public Object checkReviewAccess(ProceedingJoinPoint joinPoint,
                                    RequireReviewAccess requireReviewAccess) throws Throwable {
        long reviewId = getTarget(joinPoint, requireReviewAccess.target(), Long.class);
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));

        HttpSession session = getCurrentSession();
        if (session == null || !(isMemberAuthorized(review, session) || isGuestAuthorized(review, session))) {
            throw new UnauthorizedReviewAccessException();
        }

        return joinPoint.proceed();
    }

    private boolean isMemberAuthorized(Review review, HttpSession session) {
        GitHubMember gitHubMember = sessionManager.getGitHubMember(session);
        if (gitHubMember == null) {
            return false;
        }

        boolean hasMadeReviewGroup = reviewGroupRepository.findAllByMemberId(gitHubMember.getMemberId())
                .stream()
                .map(ReviewGroup::getId)
                .anyMatch(id -> id == review.getReviewGroupId());

        boolean hasMadeReview = review.getMemberId() != null && review.getMemberId() == gitHubMember.getMemberId();

        return hasMadeReviewGroup || hasMadeReview;
    }

    private boolean isGuestAuthorized(Review review, HttpSession session) {
        String reviewRequestCode = sessionManager.getReviewRequestCode(session);
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(UnauthorizedReviewAccessException::new);
        return reviewGroup.getId() == review.getReviewGroupId();
    }
}
