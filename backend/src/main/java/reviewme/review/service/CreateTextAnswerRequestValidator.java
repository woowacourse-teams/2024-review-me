package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.Question;
import reviewme.review.service.dto.request.CreateReviewAnswerRequest;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.service.exception.MissingRequiredQuestionAnswerException;
import reviewme.review.service.exception.TextAnswerInculdedOptionException;

@Component
@RequiredArgsConstructor
public class CreateTextAnswerRequestValidator {

    private final QuestionRepository questionRepository;

    public void validate(CreateReviewAnswerRequest request) {
        Question question = questionRepository.getQuestionById(request.questionId());
        validateNotIncludingOptions(request);
        validateQuestionRequired(question, request);
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
}
