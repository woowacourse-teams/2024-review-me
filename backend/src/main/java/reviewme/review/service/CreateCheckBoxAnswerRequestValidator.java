package reviewme.review.service;

import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question2;
import reviewme.question.repository.Question2Repository;
import reviewme.review.dto.request.create.CreateReviewAnswerRequest;
import reviewme.review.service.exception.CheckBoxAnswerIncludedNotProvidedOptionItemException;
import reviewme.review.service.exception.CheckBoxAnswerIncludedTextException;
import reviewme.review.service.exception.MissingRequiredQuestionAnswerException;
import reviewme.review.service.exception.SelectedCheckBoxAnswerCountOutOfRange;
import reviewme.template.domain.exception.OptionGroupNotFoundByQuestionIdException;
import reviewme.template.repository.OptionGroupRepository;
import reviewme.template.repository.OptionItemRepository;

@Component
@RequiredArgsConstructor
public class CreateCheckBoxAnswerRequestValidator {

    private final Question2Repository question2Repository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionItemRepository optionItemRepository;

    public void validate(CreateReviewAnswerRequest request) {
        validateNotContainingText(request);
        Question2 question = question2Repository.getQuestionById(request.questionId());
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

    private void validateRequiredQuestion(CreateReviewAnswerRequest request, Question2 question) {
        if (question.isRequired() && request.selectedOptionIds() == null) {
            throw new MissingRequiredQuestionAnswerException(question.getId());
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
            throw new SelectedCheckBoxAnswerCountOutOfRange(
                    request.questionId(),
                    request.selectedOptionIds().size(),
                    optionGroup.getMinSelectionCount(),
                    optionGroup.getMaxSelectionCount()
            );
        }
    }
}
