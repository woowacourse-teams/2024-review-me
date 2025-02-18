package reviewme.security.aspect;

import static reviewme.security.aspect.ResourceAuthorizationUtils.getCurrentSession;
import static reviewme.security.aspect.ResourceAuthorizationUtils.getTarget;

import jakarta.servlet.http.HttpSession;
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
        HttpSession session = getCurrentSession();
        if (session == null) {
            throw new ForbiddenReviewGroupAccessException();
        }

        String reviewRequestCode = getTarget(joinPoint, requireReviewGroupAccess.target(), String.class);
        ReviewGroup reviewGroup = reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));
        if (!(canMemberAccess(reviewGroup, session) || canGuestAccess(reviewGroup, session))) {
            throw new ForbiddenReviewGroupAccessException();
        }

        return joinPoint.proceed();
    }

    private boolean canMemberAccess(ReviewGroup reviewGroup, HttpSession session) {
        GitHubMember gitHubMember = sessionManager.getGitHubMember(session);
        return gitHubMember != null && reviewGroup.getMemberId() == gitHubMember.getMemberId();
    }

    private boolean canGuestAccess(ReviewGroup reviewGroup, HttpSession session) {
        String reviewRequestCode = sessionManager.getReviewRequestCode(session);
        return reviewGroup.getReviewRequestCode().equals(reviewRequestCode);
    }
}
