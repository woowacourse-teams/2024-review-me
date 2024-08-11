package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.Question2;
import reviewme.question.repository.Question2Repository;
import reviewme.review.dto.request.create.CreateReviewAnswerRequest;
import reviewme.review.service.exception.RequiredQuestionMustBeAnsweredException;
import reviewme.review.service.exception.TextAnswerInculdedOptionException;

@Component
@RequiredArgsConstructor
public class CreateTextAnswerRequestValidator {

    private final Question2Repository question2Repository;

    public void validate(CreateReviewAnswerRequest request) {
        validateNotIncludingOptions(request);
        Question2 question = validateQuestionExists(request);
        validateQuestionRequired(question, request);
    }

    private void validateNotIncludingOptions(CreateReviewAnswerRequest request) {
        if (request.selectedOptionIds() != null) {
            throw new TextAnswerInculdedOptionException();
        }
    }

    private Question2 validateQuestionExists(CreateReviewAnswerRequest request) {
        long questionId = request.questionId();
        return question2Repository.getQuestionById(questionId);
    }

    private void validateQuestionRequired(Question2 question, CreateReviewAnswerRequest request) {
        if (question.isRequired() && request.text() == null) {
            throw new RequiredQuestionMustBeAnsweredException(question.getId());
        }
    }
}
