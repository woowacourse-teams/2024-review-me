package reviewme.review.domain.policy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;
import reviewme.review.domain.Review;
import reviewme.review.domain.exception.MissingRequiredQuestionException;

@Component
public class RequiredQuestionAnswerPolicy implements ReviewRegistrationPolicy {

    @Override
    public void verify(Review review, TemplateValidationContext templateContext) {
        Set<Long> requiredQuestionIds = templateContext.getRequiredQuestionIds();
        Set<Long> reviewedQuestionIds = review.getAnsweredQuestionIds();

        if (!reviewedQuestionIds.containsAll(requiredQuestionIds)) {
            List<Long> missingRequiredQuestionIds = new ArrayList<>(requiredQuestionIds);
            missingRequiredQuestionIds.removeAll(reviewedQuestionIds);
            throw new MissingRequiredQuestionException(missingRequiredQuestionIds);
        }
    }
}
