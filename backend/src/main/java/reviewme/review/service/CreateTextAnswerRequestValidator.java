package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.Question2;
import reviewme.review.dto.request.create.CreateReviewAnswerRequest;
import reviewme.review.repository.QuestionRepository2;
import reviewme.review.service.exception.MissingRequiredQuestionAnswerException;
import reviewme.review.service.exception.TextAnswerInculdedOptionException;

@Component
@RequiredArgsConstructor
public class CreateTextAnswerRequestValidator {

    private final QuestionRepository2 questionRepository;

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
        return questionRepository.getQuestionById(questionId);
    }

    private void validateQuestionRequired(Question2 question, CreateReviewAnswerRequest request) {
        if (question.isRequired() && request.text() == null) {
            throw new MissingRequiredQuestionAnswerException(question.getId());
        }
    }
}
