package reviewme.security.aspect;

import org.springframework.stereotype.Component;

@Component
class AopTestClass {

    @RequireReviewGroupAccess
    public void testReviewGroupMethod(long reviewGroupId) {
    }

    @RequireReviewAccess
    public void testReviewMethod(long reviewId) {
    }
}
