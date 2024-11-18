package reviewme.review.domain.policy;

import java.util.HashSet;
import java.util.List;
import org.springframework.stereotype.Component;
import reviewme.review.domain.Answer;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.exception.CheckBoxAnswerIncludedNotProvidedOptionItemException;
import reviewme.review.domain.exception.SelectedOptionItemCountOutOfRangeException;
import reviewme.review.domain.policy.TemplateValidationContext.OptionGroupProperties;

@Component
public class CheckBoxAnswerRegistrationPolicy implements AnswerRegistrationPolicy {

    @Override
    public boolean support(Answer answer) {
        return answer instanceof CheckboxAnswer;
    }

    @Override
    public void verify(Answer answer, TemplateValidationContext templateContext) {
        CheckboxAnswer checkboxAnswer = (CheckboxAnswer) answer;

        OptionGroupProperties optionGroupProperties = templateContext.getOptionGroupPropertiesByQuestionId(
                checkboxAnswer.getQuestionId());
        List<Long> optionItemIds = templateContext.getOptionItemIdsByOptionGroupId(optionGroupProperties.getId());
        validateOnlyIncludingProvidedOptionItem(checkboxAnswer, optionItemIds);
        validateCheckedOptionItemCount(checkboxAnswer, optionGroupProperties);
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
