package reviewme.security.aspect;

import static reviewme.security.aspect.ResourceAuthorizationUtils.getCurrentRequest;
import static reviewme.security.aspect.ResourceAuthorizationUtils.getTarget;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reviewme.auth.domain.GitHubMember;
import reviewme.review.domain.Review;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.exception.ReviewNotFoundException;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.security.aspect.exception.ForbiddenReviewAccessException;
import reviewme.security.session.SessionManager;

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
        long reviewId = getTarget(joinPoint, requireReviewAccess.target(), Long.class);
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));

        HttpServletRequest request = getCurrentRequest();
        if (!(canMemberAccess(review, request) || canGuestAccess(review, request))) {
            throw new ForbiddenReviewAccessException();
        }

        return joinPoint.proceed();
    }

    private boolean canMemberAccess(Review review, HttpServletRequest request) {
        GitHubMember gitHubMember = sessionManager.getGitHubMember(request);
        if (gitHubMember == null) {
            return false;
        }

        boolean isReviewGroupCreator = reviewGroupRepository.existsByIdAndMemberId(
                review.getReviewGroupId(), gitHubMember.getMemberId()
        );
        return isReviewGroupCreator || review.isMadeByMember(gitHubMember.getMemberId());
    }

    private boolean canGuestAccess(Review review, HttpServletRequest request) {
        String reviewRequestCode = sessionManager.getReviewRequestCode(request);
        if (reviewRequestCode == null) {
            return false;
        }

        return reviewGroupRepository.existsByIdAndReviewRequestCode(review.getReviewGroupId(), reviewRequestCode);
    }
}
