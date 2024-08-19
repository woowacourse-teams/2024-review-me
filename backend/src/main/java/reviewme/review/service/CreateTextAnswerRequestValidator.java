package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.exception.InvalidTextAnswerLengthException;
import reviewme.review.service.dto.request.CreateReviewAnswerRequest;
import reviewme.review.service.exception.MissingRequiredQuestionAnswerException;
import reviewme.review.service.exception.TextAnswerInculdedOptionException;

@Component
@RequiredArgsConstructor
public class CreateTextAnswerRequestValidator {

    private static final int MIN_LENGTH = 20;
    private static final int MAX_LENGTH = 1_000;

    private final QuestionRepository questionRepository;

    public void validate(CreateReviewAnswerRequest request) {
        Question question = questionRepository.getQuestionById(request.questionId());
        validateNotIncludingOptions(request);
        validateQuestionRequired(question, request);
        validateLength(request);
    }

    private void validateNotIncludingOptions(CreateReviewAnswerRequest request) {
        if (request.selectedOptionIds() != null) {
            throw new TextAnswerInculdedOptionException();
        }
    }

    private void validateQuestionRequired(Question question, CreateReviewAnswerRequest request) {
        if (question.isRequired() && request.text() == null) {
            throw new MissingRequiredQuestionAnswerException(question.getId());
        }
    }

    private void validateLength(CreateReviewAnswerRequest request) {
        int textLength = request.text().length();
        if (textLength < MIN_LENGTH || textLength > MAX_LENGTH) {
            throw new InvalidTextAnswerLengthException(textLength, MIN_LENGTH, MAX_LENGTH);
        }
    }
}
