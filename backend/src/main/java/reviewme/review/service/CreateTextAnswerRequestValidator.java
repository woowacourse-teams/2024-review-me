package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.exception.InvalidTextAnswerLengthException;
import reviewme.review.service.dto.request.CreateReviewAnswerRequest;
import reviewme.review.service.exception.MissingRequiredQuestionAnswerException;
import reviewme.review.service.exception.TextAnswerInculdedOptionException;
import reviewme.review.service.dto.request.CreateReviewAnswerRequest;
import reviewme.review.service.exception.MissingRequiredAnswerException;
import reviewme.review.service.exception.SubmittedQuestionNotFoundException;
import reviewme.review.service.exception.TextAnswerIncludedOptionItemException;

@Component
@RequiredArgsConstructor
public class CreateTextAnswerRequestValidator {

    private static final int MIN_LENGTH = 20;
    private static final int MAX_LENGTH = 1_000;

    private final QuestionRepository questionRepository;

    public void validate(CreateReviewAnswerRequest request) {
        Question question = questionRepository.findById(request.questionId())
                .orElseThrow(() -> new SubmittedQuestionNotFoundException(request.questionId()));
        validateNotIncludingOptions(request);
        validateQuestionRequired(question, request);
        validateLength(request);
    }

    private void validateNotIncludingOptions(CreateReviewAnswerRequest request) {
        if (request.selectedOptionIds() != null) {
            throw new TextAnswerIncludedOptionItemException();
        }
    }

    private void validateQuestionRequired(Question question, CreateReviewAnswerRequest request) {
        if (question.isRequired() && request.text() == null) {
            throw new MissingRequiredAnswerException(question.getId());
        }
    }

    private void validateLength(CreateReviewAnswerRequest request) {
        int textLength = request.text().length();
        if (textLength < MIN_LENGTH || textLength > MAX_LENGTH) {
            throw new InvalidTextAnswerLengthException(textLength, MIN_LENGTH, MAX_LENGTH);
        }
    }
}
