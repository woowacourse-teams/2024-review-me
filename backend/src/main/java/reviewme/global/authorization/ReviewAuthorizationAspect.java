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
import reviewme.global.authorization.exception.ReviewGroupNotExistsBySessionReviewRequestCodeException;
import reviewme.global.authorization.exception.ForbiddenReviewAccessException;
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

    @Around("@annotation(requireReviewAccess)")
    public Object checkReviewAccess(ProceedingJoinPoint joinPoint,
                                    RequireReviewAccess requireReviewAccess) throws Throwable {
        HttpSession session = getCurrentSession();
        if (session == null) {
            throw new ForbiddenReviewAccessException();
        }

        long reviewId = getTarget(joinPoint, requireReviewAccess.target(), Long.class);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
        if (!(canMemberAccess(review, session) || canGuestAccess(review, session))) {
            throw new ForbiddenReviewAccessException();
        }

        return joinPoint.proceed();
    }

    private boolean canMemberAccess(Review review, HttpSession session) {
        GitHubMember gitHubMember = sessionManager.getGitHubMember(session);
        if (gitHubMember == null) {
            return false;
        }

        boolean isReviewGroupCreator = reviewGroupRepository.existsByIdAndMemberId(
                review.getReviewGroupId(), gitHubMember.getMemberId()
        );
        boolean isReviewAuthor = review.getMemberId() != null && review.getMemberId() == gitHubMember.getMemberId();

        return isReviewGroupCreator || isReviewAuthor;
    }

    private boolean canGuestAccess(Review review, HttpSession session) {
        String reviewRequestCode = sessionManager.getReviewRequestCode(session);
        if (reviewRequestCode == null) {
            return false;
        }

        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotExistsBySessionReviewRequestCodeException(reviewRequestCode));

        return review.getReviewGroupId() == reviewGroup.getId();
    }
}
