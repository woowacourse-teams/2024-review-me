package reviewme.review.domain.policy;

import reviewme.review.domain.Review;

public interface ReviewRegistrationPolicy {

    void verify(Review review, TemplateValidationContext templateContext);
}
