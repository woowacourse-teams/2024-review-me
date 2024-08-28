package reviewme.review.service.module;

import org.springframework.stereotype.Component;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.exception.InvalidTextAnswerLengthException;
import reviewme.review.service.exception.QuestionNotAnsweredException;

@Component
public class TextAnswerValidator {

    private static final int MIN_LENGTH = 20;
    private static final int MAX_LENGTH = 1_000;

    public void validate(TextAnswer textAnswer) {
        validateAnswerExist(textAnswer);
        validateLength(textAnswer);
    }

    private static void validateAnswerExist(TextAnswer textAnswer) {
        if (textAnswer.getContent() == null) {
            throw new QuestionNotAnsweredException(textAnswer.getId());
        }
    }

    private void validateLength(TextAnswer textAnswer) {
        int answerLength = textAnswer.getContent().length();
        if (answerLength < MIN_LENGTH || answerLength > MAX_LENGTH) {
            throw new InvalidTextAnswerLengthException(answerLength, MIN_LENGTH, MAX_LENGTH);
        }
    }
}
