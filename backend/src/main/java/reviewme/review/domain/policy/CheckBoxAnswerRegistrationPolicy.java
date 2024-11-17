package reviewme.review.domain.policy;

import java.util.HashSet;
import java.util.List;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.policy.TemplateValidationContext.OptionGroupProperties;
import reviewme.review.domain.exception.CheckBoxAnswerIncludedNotProvidedOptionItemException;
import reviewme.review.domain.exception.SelectedOptionItemCountOutOfRangeException;

@Component
@Order(1)
public class CheckBoxAnswerRegistrationPolicy implements ReviewRegistrationPolicy {

    @Override
    public void verify(Review review, TemplateValidationContext templateContext) {
        List<CheckboxAnswer> checkboxAnswers = review.getAnswersByType(CheckboxAnswer.class);

        for (CheckboxAnswer checkboxAnswer : checkboxAnswers) {
            OptionGroupProperties optionGroupProperties = templateContext.getOptionGroupPropertiesByQuestionId(
                    checkboxAnswer.getQuestionId());
            List<Long> optionItemIds = templateContext.getOptionItemIdsByOptionGroupId(optionGroupProperties.getId());
            validateOnlyIncludingProvidedOptionItem(checkboxAnswer, optionItemIds);
            validateCheckedOptionItemCount(checkboxAnswer, optionGroupProperties);
        }
    }

    private void validateOnlyIncludingProvidedOptionItem(CheckboxAnswer checkboxAnswer, List<Long> optionItemIds) {
        List<Long> answeredOptionItemIds = checkboxAnswer.getSelectedOptionIds();
        if (!new HashSet<>(optionItemIds).containsAll(answeredOptionItemIds)) {
            throw new CheckBoxAnswerIncludedNotProvidedOptionItemException(
                    checkboxAnswer.getQuestionId(), optionItemIds, answeredOptionItemIds
            );
        }
    }

    private void validateCheckedOptionItemCount(CheckboxAnswer checkboxAnswer,
                                                OptionGroupProperties optionGroupProperties) {
        int answeredOptionItemCount = checkboxAnswer.getSelectedOptionIds().size();

        if (answeredOptionItemCount < optionGroupProperties.getMinSelectionCount()
            || answeredOptionItemCount > optionGroupProperties.getMaxSelectionCount()) {
            throw new SelectedOptionItemCountOutOfRangeException(
                    checkboxAnswer.getQuestionId(),
                    answeredOptionItemCount,
                    optionGroupProperties.getMinSelectionCount(),
                    optionGroupProperties.getMaxSelectionCount()
            );
        }
    }
}
