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
import reviewme.global.authorization.exception.ForbiddenReviewGroupAccessException;
import reviewme.global.session.SessionManager;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.domain.exception.ReviewGroupNotFoundException;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

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

        long reviewGroupId = getTarget(joinPoint, requireReviewGroupAccess.target(), Long.class);
        ReviewGroup reviewGroup = reviewGroupRepository.findById(reviewGroupId)
                .orElseThrow(() -> new ReviewGroupNotFoundException(reviewGroupId));
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
