package reviewme.review.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.review.domain.Answer;
import reviewme.review.domain.Review;
import reviewme.review.domain.policy.AnswerRegistrationPolicy;
import reviewme.review.domain.policy.ReviewRegistrationPolicy;
import reviewme.review.domain.policy.TemplateValidationContext;

@Component
@RequiredArgsConstructor
public class ReviewRegistrationPolicyProcessor {

    private final List<AnswerRegistrationPolicy> answerRegistrationPolicies;
    private final List<ReviewRegistrationPolicy> reviewRegistrationPolicies;

    public void verifyRegistrationPolicy(Review review, TemplateValidationContext templateContext) {
        verifyAnswerRegistrationPolicy(review.getAnswers(), templateContext);
        verifyReviewRegistrationPolicy(review, templateContext);
    }

    private void verifyAnswerRegistrationPolicy(List<Answer> answers, TemplateValidationContext templateContext) {
        for (Answer answer : answers) {
            answerRegistrationPolicies.stream()
                    .filter(policy -> policy.support(answer))
                    .forEach(policy -> policy.verify(answer, templateContext));
        }
    }

    private void verifyReviewRegistrationPolicy(Review review, TemplateValidationContext templateContext) {
        for (ReviewRegistrationPolicy policy : reviewRegistrationPolicies) {
            policy.verify(review, templateContext);
        }
    }
}
