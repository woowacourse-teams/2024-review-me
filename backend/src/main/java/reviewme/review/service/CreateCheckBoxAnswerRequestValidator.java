package reviewme.review.service;

import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question2;
import reviewme.question.repository.Question2Repository;
import reviewme.review.dto.request.create.CreateReviewAnswerRequest;
import reviewme.review.service.exception.CheckBoxAnswerIncludedNotProvidedOptionItemException;
import reviewme.review.service.exception.CheckBoxAnswerIncludedTextException;
import reviewme.review.service.exception.RequiredQuestionMustBeAnsweredException;
import reviewme.review.service.exception.SelectedCheckBoxAnswerCountOutOfRange;
import reviewme.template.domain.exception.OptionGroupNotFoundException;
import reviewme.template.repository.OptionGroupRepository;
import reviewme.template.repository.OptionItemRepository;

@Service
@RequiredArgsConstructor
public class CreateCheckBoxAnswerRequestValidator {

    private final Question2Repository question2Repository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionItemRepository optionItemRepository;

    public void validate(CreateReviewAnswerRequest request) {
        validateNotContainingText(request);
        Question2 question = validateQuestionExists(request);
        OptionGroup optionGroup = validateOptionGroupExists(question);
        validateQuestionRequired(request, question);
        validateOnlyIncludingProvidedOptionItem(request, optionGroup);
        validateCheckedOptionItemCount(request, optionGroup);
    }

    private void validateNotContainingText(CreateReviewAnswerRequest request) {
        if (request.text() != null) {
            throw new CheckBoxAnswerIncludedTextException();
        }
    }

    private Question2 validateQuestionExists(CreateReviewAnswerRequest request) {
        long questionId = request.questionId();
        return question2Repository.getQuestionById(questionId);
    }

    private OptionGroup validateOptionGroupExists(Question2 question) {
        return optionGroupRepository.findByQuestionId(question.getId())
                .orElseThrow(() -> new OptionGroupNotFoundException(question.getId()));
    }

    private void validateQuestionRequired(CreateReviewAnswerRequest request, Question2 question) {
        if (question.isRequired() && request.selectedOptionIds() == null) {
            throw new RequiredQuestionMustBeAnsweredException(question.getId());
        }
    }

    private void validateOnlyIncludingProvidedOptionItem(CreateReviewAnswerRequest request, OptionGroup optionGroup) {
        List<Long> providedOptionItemIds = optionItemRepository.findAllByOptionGroupId(optionGroup.getId())
                .stream()
                .map(OptionItem::getId)
                .toList();
        List<Long> submittedOptionItemIds = request.selectedOptionIds();

        if (!new HashSet<>(providedOptionItemIds).containsAll(submittedOptionItemIds)) {
            throw new CheckBoxAnswerIncludedNotProvidedOptionItemException(providedOptionItemIds,
                    submittedOptionItemIds
            );
        }
    }

    private void validateCheckedOptionItemCount(CreateReviewAnswerRequest request, OptionGroup optionGroup) {
        if (request.selectedOptionIds().size() < optionGroup.getMinSelectionCount()
                || request.selectedOptionIds().size() > optionGroup.getMaxSelectionCount()) {
            throw new SelectedCheckBoxAnswerCountOutOfRange(
                    request.selectedOptionIds().size(),
                    optionGroup.getMinSelectionCount(),
                    optionGroup.getMaxSelectionCount()
            );
        }
    }
}
