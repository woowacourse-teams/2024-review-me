package reviewme.review.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.domain.Review;
import reviewme.review.domain.policy.ReviewRegistrationPolicy;
import reviewme.review.domain.policy.TemplateValidationContext;
import reviewme.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewRegisterService {

    private final List<ReviewRegistrationPolicy> registrationPolicies;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Review register(Review review, TemplateValidationContext templateContext) {
        for (ReviewRegistrationPolicy registrationPolicy : registrationPolicies) {
            registrationPolicy.verify(review, templateContext);
        }

        return reviewRepository.save(review);
    }
}
