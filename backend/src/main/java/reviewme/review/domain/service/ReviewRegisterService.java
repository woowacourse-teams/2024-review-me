package reviewme.review.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.review.domain.Review;
import reviewme.review.domain.policy.TemplateValidationContext;
import reviewme.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewRegisterService {

    private final ReviewRegistrationPolicyProcessor policyProcessor;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Review register(Review review, TemplateValidationContext templateContext) {
        policyProcessor.verifyRegistrationPolicy(review, templateContext);
        return reviewRepository.save(review);
    }
}
