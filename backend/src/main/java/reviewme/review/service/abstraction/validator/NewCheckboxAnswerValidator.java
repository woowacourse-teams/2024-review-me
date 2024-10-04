package reviewme.review.service.abstraction.validator;

import java.util.HashSet;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.abstraction.Answer;
import reviewme.review.domain.abstraction.NewCheckBoxAnswerSelectedOption;
import reviewme.review.domain.abstraction.NewCheckboxAnswer;
import reviewme.review.service.exception.CheckBoxAnswerIncludedNotProvidedOptionItemException;
import reviewme.review.service.exception.OptionGroupNotFoundByQuestionIdException;
import reviewme.review.service.exception.SelectedOptionItemCountOutOfRangeException;
import reviewme.review.service.exception.SubmittedQuestionNotFoundException;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class NewCheckboxAnswerValidator implements NewAnswerValidator {

    private final QuestionRepository questionRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionItemRepository optionItemRepository;

    @Override
    public boolean supports(Class<? extends Answer> answerClass) {
        return NewCheckboxAnswer.class.isAssignableFrom(answerClass);
    }

    @Override
    public void validate(Answer answer) {
        NewCheckboxAnswer checkboxAnswer = (NewCheckboxAnswer) answer;
        Question question = questionRepository.findById(checkboxAnswer.getQuestionId())
                .orElseThrow(() -> new SubmittedQuestionNotFoundException(checkboxAnswer.getQuestionId()));

        OptionGroup optionGroup = optionGroupRepository.findByQuestionId(question.getId())
                .orElseThrow(() -> new OptionGroupNotFoundByQuestionIdException(question.getId()));

        validateOnlyIncludingProvidedOptionItem(checkboxAnswer, optionGroup);
        validateCheckedOptionItemCount(checkboxAnswer, optionGroup);
    }

    private void validateOnlyIncludingProvidedOptionItem(NewCheckboxAnswer checkboxAnswer, OptionGroup optionGroup) {
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

    private void validateCheckedOptionItemCount(NewCheckboxAnswer checkboxAnswer, OptionGroup optionGroup) {
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

    private List<Long> extractAnsweredOptionItemIds(NewCheckboxAnswer checkboxAnswer) {
        return checkboxAnswer.getSelectedOptionIds()
                .stream()
                .map(NewCheckBoxAnswerSelectedOption::getSelectedOptionId)
                .toList();
    }
}
