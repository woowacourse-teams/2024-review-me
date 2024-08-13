package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.Question;
import reviewme.review.dto.request.CreateReviewAnswerRequest;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.service.exception.MissingRequiredQuestionAnswerException;
import reviewme.review.service.exception.TextAnswerInculdedOptionException;

@Component
@RequiredArgsConstructor
public class CreateTextAnswerRequestValidator {

    private final QuestionRepository questionRepository;

    public void validate(CreateReviewAnswerRequest request) {
        validateNotIncludingOptions(request);
        Question question = validateQuestionExists(request);
        validateQuestionRequired(question, request);
    }

    private void validateNotIncludingOptions(CreateReviewAnswerRequest request) {
        if (request.selectedOptionIds() != null) {
            throw new TextAnswerInculdedOptionException();
        }
    }

    private Question validateQuestionExists(CreateReviewAnswerRequest request) {
        long questionId = request.questionId();
        return questionRepository.getQuestionById(questionId);
    }

    private void validateQuestionRequired(Question question, CreateReviewAnswerRequest request) {
        if (question.isRequired() && request.text() == null) {
            throw new MissingRequiredQuestionAnswerException(question.getId());
        }
    }
}
