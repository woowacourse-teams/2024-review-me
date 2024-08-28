package reviewme.review.service.module;

import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.review.domain.CheckBoxAnswerSelectedOption;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.service.exception.CheckBoxAnswerIncludedNotProvidedOptionItemException;
import reviewme.review.service.exception.QuestionNotAnsweredException;
import reviewme.review.service.exception.SelectedOptionItemCountOutOfRangeException;
import reviewme.template.domain.exception.OptionGroupNotFoundByQuestionIdException;

@Component
@RequiredArgsConstructor
public class CheckBoxAnswerValidator {

    private final OptionGroupRepository optionGroupRepository;
    private final OptionItemRepository optionItemRepository;

    public void validate(CheckboxAnswer checkboxAnswer) {
        OptionGroup optionGroup = optionGroupRepository.findByQuestionId(checkboxAnswer.getQuestionId())
                .orElseThrow(() -> new OptionGroupNotFoundByQuestionIdException(checkboxAnswer.getQuestionId()));

        validateAnswerExist(checkboxAnswer);
        validateOnlyIncludingProvidedOptionItem(checkboxAnswer, optionGroup);
        validateCheckedOptionItemCount(checkboxAnswer, optionGroup);
    }

    private void validateAnswerExist(CheckboxAnswer checkboxAnswer) {
        if (checkboxAnswer.getSelectedOptionIds() == null || checkboxAnswer.getSelectedOptionIds().isEmpty()) {
            throw new QuestionNotAnsweredException(checkboxAnswer.getId());
        }
    }

    private void validateOnlyIncludingProvidedOptionItem(CheckboxAnswer checkboxAnswer, OptionGroup optionGroup) {
        List<Long> providedOptionItemIds = optionItemRepository.findAllByOptionGroupId(optionGroup.getId())
                .stream()
                .map(OptionItem::getId)
                .toList();
        List<Long> answeredOptionItemIds = extractAnsweredOptionItemIds(checkboxAnswer);

        if (!new HashSet<>(providedOptionItemIds).containsAll(answeredOptionItemIds)) {
            throw new CheckBoxAnswerIncludedNotProvidedOptionItemException(
                    checkboxAnswer.getQuestionId(), providedOptionItemIds, answeredOptionItemIds
            );
        }
    }

    private void validateCheckedOptionItemCount(CheckboxAnswer checkboxAnswer, OptionGroup optionGroup) {
        int answeredOptionItemCount = extractAnsweredOptionItemIds(checkboxAnswer).size();

        if (answeredOptionItemCount < optionGroup.getMinSelectionCount()
                || answeredOptionItemCount > optionGroup.getMaxSelectionCount()) {
            throw new SelectedOptionItemCountOutOfRangeException(
                    checkboxAnswer.getQuestionId(),
                    answeredOptionItemCount,
                    optionGroup.getMinSelectionCount(),
                    optionGroup.getMaxSelectionCount()
            );
        }
    }

    private List<Long> extractAnsweredOptionItemIds(CheckboxAnswer checkboxAnswer) {
        return checkboxAnswer.getSelectedOptionIds()
                .stream()
                .map(CheckBoxAnswerSelectedOption::getSelectedOptionId)
                .toList();
    }
}
