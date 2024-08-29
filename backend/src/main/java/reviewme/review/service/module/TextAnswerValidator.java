package reviewme.review.service.module;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.exception.InvalidTextAnswerLengthException;
import reviewme.review.service.exception.SubmittedQuestionNotFoundException;

@Component
@RequiredArgsConstructor
public class TextAnswerValidator {

    private static final int MIN_LENGTH = 20;
    private static final int MAX_LENGTH = 1_000;

    private final QuestionRepository questionRepository;

    public void validate(TextAnswer textAnswer) {
        validateExistQuestion(textAnswer);
        validateLength(textAnswer);
    }

    private void validateExistQuestion(TextAnswer textAnswer) {
        if (!questionRepository.existsById(textAnswer.getQuestionId())) {
            throw new SubmittedQuestionNotFoundException(textAnswer.getQuestionId());
        }
    }

    private void validateLength(TextAnswer textAnswer) {
        int answerLength = textAnswer.getContent().length();
        if (answerLength < MIN_LENGTH || answerLength > MAX_LENGTH) {
            throw new InvalidTextAnswerLengthException(answerLength, MIN_LENGTH, MAX_LENGTH);
        }
    }
}
