package reviewme.review.domain.policy;

import java.util.Set;
import org.springframework.stereotype.Component;
import reviewme.review.domain.Review;
import reviewme.review.domain.exception.SubmittedQuestionAndProvidedQuestionMismatchException;

@Component
public class ProvidedAndSubmittedQuestionPolicy implements ReviewRegistrationPolicy {

    @Override
    public void verify(Review review, TemplateValidationContext templateContext) {
        Set<Long> providedQuestionIds = templateContext.getQuestionIds();
        Set<Long> reviewedQuestionIds = review.getAnsweredQuestionIds();
        if (!providedQuestionIds.containsAll(reviewedQuestionIds)) {
            throw new SubmittedQuestionAndProvidedQuestionMismatchException(reviewedQuestionIds, providedQuestionIds);
        }
    }
}
