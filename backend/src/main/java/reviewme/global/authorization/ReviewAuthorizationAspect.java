package reviewme.global.authorization;

import static reviewme.global.authorization.ResourceAuthorizationUtils.getCurrentSession;
import static reviewme.global.authorization.ResourceAuthorizationUtils.getTarget;

import jakarta.servlet.http.HttpSession;
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
import reviewme.reviewgroup.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;

@Aspect
@Component
@RequiredArgsConstructor
public class ReviewAuthorizationAspect {

    private final SessionManager sessionManager;
    private final ReviewRepository reviewRepository;
    private final ReviewGroupRepository reviewGroupRepository;

    @Around("@annotation(requireReviewAccess)")
    public Object checkReviewAccess(ProceedingJoinPoint joinPoint,
                                    RequireReviewAccess requireReviewAccess) throws Throwable {
        HttpSession session = getCurrentSession();
        if (session == null) {
            throw new UnauthorizedReviewAccessException();
        }

        long reviewId = getTarget(joinPoint, requireReviewAccess.target(), Long.class);
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));
        if (!(isMemberAuthorized(review, session) || isGuestAuthorized(review, session))) {
            throw new UnauthorizedReviewAccessException();
        }

        return joinPoint.proceed();
    }

    private boolean isMemberAuthorized(Review review, HttpSession session) {
        GitHubMember gitHubMember = sessionManager.getGitHubMember(session);
        if (gitHubMember == null) {
            return false;
        }

        boolean isReviewGroupCreator = reviewGroupRepository.findAllByMemberId(gitHubMember.getMemberId())
                .stream()
                .map(ReviewGroup::getId)
                .anyMatch(id -> id == review.getReviewGroupId());
        boolean isReviewAuthor = review.getMemberId() != null && review.getMemberId() == gitHubMember.getMemberId();

        return isReviewGroupCreator || isReviewAuthor;
    }

    private boolean isGuestAuthorized(Review review, HttpSession session) {
        String reviewRequestCode = sessionManager.getReviewRequestCode(session);
        if (reviewRequestCode == null) {
            return false;
        }

        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));

        return review.getReviewGroupId() == reviewGroup.getId();
    }
}
