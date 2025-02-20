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
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.reviewgroup.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.security.aspect.exception.ForbiddenReviewGroupAccessException;
import reviewme.security.session.SessionManager;

@Aspect
@Component
@RequiredArgsConstructor
public class ReviewGroupAuthorizationAspect {

    private final SessionManager sessionManager;
    private final ReviewGroupRepository reviewGroupRepository;

    @Around("@annotation(requireReviewGroupAccess)")
    public Object checkReviewGroupAccess(ProceedingJoinPoint joinPoint,
                                         RequireReviewGroupAccess requireReviewGroupAccess) throws Throwable {
        String reviewRequestCode = getTarget(joinPoint, requireReviewGroupAccess.target(), String.class);
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));

        HttpServletRequest request = getCurrentRequest();
        if (!(canMemberAccess(reviewGroup, request) || canGuestAccess(reviewGroup, request))) {
            throw new ForbiddenReviewGroupAccessException();
        }

        return joinPoint.proceed();
    }

    private boolean canMemberAccess(ReviewGroup reviewGroup, HttpServletRequest request) {
        GitHubMember gitHubMember = sessionManager.getGitHubMember(request);
        return gitHubMember != null && reviewGroup.isMadeByMember(gitHubMember.getMemberId());
    }

    private boolean canGuestAccess(ReviewGroup reviewGroup, HttpServletRequest request) {
        String reviewRequestCode = sessionManager.getReviewRequestCode(request);
        return reviewGroup.getReviewRequestCode().equals(reviewRequestCode);
    }
}
