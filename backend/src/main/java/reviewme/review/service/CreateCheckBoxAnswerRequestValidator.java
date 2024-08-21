package reviewme.review.service;

import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.service.dto.request.CreateReviewAnswerRequest;
import reviewme.review.service.exception.CheckBoxAnswerIncludedNotProvidedOptionItemException;
import reviewme.review.service.exception.CheckBoxAnswerIncludedTextException;
import reviewme.review.service.exception.RequiredQuestionNotAnsweredException;
import reviewme.review.service.exception.SelectedOptionItemCountOutOfRangeException;
import reviewme.review.service.exception.SubmittedQuestionNotFoundException;
import reviewme.template.domain.exception.OptionGroupNotFoundByQuestionIdException;

@Component
@RequiredArgsConstructor
public class CreateCheckBoxAnswerRequestValidator {

    private final QuestionRepository questionRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionItemRepository optionItemRepository;

    public void validate(CreateReviewAnswerRequest request) {
        validateNotContainingText(request);
        Question question = questionRepository.findById(request.questionId())
                .orElseThrow(() -> new SubmittedQuestionNotFoundException(request.questionId()));
        OptionGroup optionGroup = optionGroupRepository.findByQuestionId(question.getId())
                .orElseThrow(() -> new OptionGroupNotFoundByQuestionIdException(question.getId()));
        validateRequiredQuestion(request, question);
        validateOnlyIncludingProvidedOptionItem(request, optionGroup);
        validateCheckedOptionItemCount(request, optionGroup);
    }

    private void validateNotContainingText(CreateReviewAnswerRequest request) {
        if (request.text() != null) {
            throw new CheckBoxAnswerIncludedTextException();
        }
    }

    private void validateRequiredQuestion(CreateReviewAnswerRequest request, Question question) {
        if (question.isRequired() && request.selectedOptionIds() == null) {
            throw new RequiredQuestionNotAnsweredException(question.getId());
        }
    }

    private void validateOnlyIncludingProvidedOptionItem(CreateReviewAnswerRequest request, OptionGroup optionGroup) {
        List<Long> providedOptionItemIds = optionItemRepository.findAllByOptionGroupId(optionGroup.getId())
                .stream()
                .map(OptionItem::getId)
                .toList();
        List<Long> submittedOptionItemIds = request.selectedOptionIds();

        if (!new HashSet<>(providedOptionItemIds).containsAll(submittedOptionItemIds)) {
            throw new CheckBoxAnswerIncludedNotProvidedOptionItemException(
                    request.questionId(), providedOptionItemIds, submittedOptionItemIds
            );
        }
    }

    private void validateCheckedOptionItemCount(CreateReviewAnswerRequest request, OptionGroup optionGroup) {
        if (request.selectedOptionIds().size() < optionGroup.getMinSelectionCount()
                || request.selectedOptionIds().size() > optionGroup.getMaxSelectionCount()) {
            throw new SelectedOptionItemCountOutOfRangeException(
                    request.questionId(),
                    request.selectedOptionIds().size(),
                    optionGroup.getMinSelectionCount(),
                    optionGroup.getMaxSelectionCount()
            );
        }
    }
}
