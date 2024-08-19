package reviewme.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.service.dto.request.CreateReviewAnswerRequest;
import reviewme.review.service.exception.MissingRequiredAnswerException;
import reviewme.review.service.exception.SubmittedQuestionNotFoundException;
import reviewme.review.service.exception.TextAnswerInculdedOptionItemException;

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
            throw new TextAnswerInculdedOptionItemException();
        }
    }

    private Question validateQuestionExists(CreateReviewAnswerRequest request) {
        long questionId = request.questionId();
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new SubmittedQuestionNotFoundException(questionId));
    }

    private void validateQuestionRequired(Question question, CreateReviewAnswerRequest request) {
        if (question.isRequired() && request.text() == null) {
            throw new MissingRequiredAnswerException(question.getId());
        }
    }
}
