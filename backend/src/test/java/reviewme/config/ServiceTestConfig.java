package reviewme.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import reviewme.global.authorization.RequireReviewAccess;
import reviewme.global.authorization.RequireReviewGroupAccess;
import reviewme.global.authorization.ReviewAuthorizationAspect;
import reviewme.global.authorization.ReviewGroupAuthorizationAspect;
import reviewme.global.session.SessionManager;
import reviewme.review.repository.ReviewRepository;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.CacheCleaner;
import reviewme.support.DatabaseCleaner;

@TestConfiguration
public class ServiceTestConfig {

    @Bean
    public DatabaseCleaner databaseCleaner() {
        return new DatabaseCleaner();
    }

    @Bean
    public CacheCleaner cacheCleaner(CacheManager cacheManager) {
        return new CacheCleaner(cacheManager);
    }

    /*
    * 서비스 코드 테스트에 제외할 aop 들
    * */
    @Bean
    public ReviewAuthorizationAspect reviewAuthorizationAspect(SessionManager sessionManager,
                                                               ReviewRepository reviewRepository,
                                                               ReviewGroupRepository reviewGroupRepository) {
        return new ReviewAuthorizationAspect(sessionManager, reviewRepository, reviewGroupRepository) {
            @Override
            @Around("@annotation(requireReviewAccess)")
            public Object checkReviewAccess(ProceedingJoinPoint joinPoint,
                                            RequireReviewAccess requireReviewAccess) throws Throwable {
                return joinPoint.proceed();
            }
        };
    }

    @Bean
    public ReviewGroupAuthorizationAspect reviewGroupAuthorizationAspect(SessionManager sessionManager,
                                                                         ReviewGroupRepository reviewGroupRepository) {
        return new ReviewGroupAuthorizationAspect(sessionManager, reviewGroupRepository) {
            @Override
            @Around("@annotation(requireReviewGroupAccess)")
            public Object checkReviewGroupAccess(ProceedingJoinPoint joinPoint,
                                                 RequireReviewGroupAccess requireReviewGroupAccess) throws Throwable {
                return joinPoint.proceed();
            }
        };
    }
}
