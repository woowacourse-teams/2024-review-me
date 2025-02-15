package reviewme.global.authorization;

import org.springframework.stereotype.Component;
import reviewme.security.aspect.RequireReviewAccess;
import reviewme.security.aspect.RequireReviewGroupAccess;

@Component
class AopTestClass {

    @RequireReviewGroupAccess
    public void testReviewGroupMethod(long reviewGroupId) {
    }

    @RequireReviewAccess
    public void testReviewMethod(long reviewId) {
    }
}
