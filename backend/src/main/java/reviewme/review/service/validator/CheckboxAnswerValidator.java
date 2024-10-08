package reviewme.review.service.validator;

import java.util.HashSet;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.cache.TemplateCacheRepository;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.review.domain.Answer;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.CheckboxAnswerSelectedOption;
import reviewme.review.service.exception.CheckBoxAnswerIncludedNotProvidedOptionItemException;
import reviewme.review.service.exception.SelectedOptionItemCountOutOfRangeException;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckboxAnswerValidator implements AnswerValidator {

    private final TemplateCacheRepository templateCacheRepository;

    @Override
    public boolean supports(Class<? extends Answer> answerClass) {
        return CheckboxAnswer.class.isAssignableFrom(answerClass);
    }

    @Override
    public void validate(Answer answer) {
        CheckboxAnswer checkboxAnswer = (CheckboxAnswer) answer;
        Question question = templateCacheRepository.findQuestionById(checkboxAnswer.getQuestionId());
        OptionGroup optionGroup = templateCacheRepository.findOptionGroupByQuestionId(question.getId());

        validateOnlyIncludingProvidedOptionItem(checkboxAnswer, optionGroup);
        validateCheckedOptionItemCount(checkboxAnswer, optionGroup);
    }

    private void validateOnlyIncludingProvidedOptionItem(CheckboxAnswer checkboxAnswer, OptionGroup optionGroup) {
        List<Long> providedOptionItemIds = templateCacheRepository.findAllOptionItemByOptionGroupId(optionGroup.getId())
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
                .map(CheckboxAnswerSelectedOption::getSelectedOptionId)
                .toList();
    }
}
