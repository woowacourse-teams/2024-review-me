package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.question.repository.Question2Repository;
import reviewme.review.dto.request.create.CreateReviewAnswerRequest;
import reviewme.review.service.exception.TextAnswerIncudedOptionException;

@Service
@RequiredArgsConstructor
public class CreateTextAnswerRequestValidator {

    private final Question2Repository question2Repository;

    public void validate(CreateReviewAnswerRequest request) {
        validateNotIncludingOptions(request);
        validateQuestionExists(request);
    }

    private static void validateNotIncludingOptions(CreateReviewAnswerRequest request) {
        if (!request.selectedOptionIds().isEmpty()) {
            throw new TextAnswerIncudedOptionException();
        }
    }

    private void validateQuestionExists(CreateReviewAnswerRequest request) {
        long questionId = request.questionId();
        question2Repository.getQuestionById(questionId);
    }
}
