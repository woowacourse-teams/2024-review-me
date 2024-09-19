package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class InvalidTextAnswerLengthException extends BadRequestException {

    public InvalidTextAnswerLengthException(long questionId, int answerLength, int minLength, int maxLength) {
        super("답변의 길이는 %d자 이상 %d자 이하여야 해요.".formatted(minLength, maxLength));
        log.warn("AnswerLength is out of bound - questionId: {}, answerLength: {}, minLength: {}, maxLength: {}",
                questionId, answerLength, minLength, maxLength, this);
    }

    public InvalidTextAnswerLengthException(long questionId, int answerLength, int maxLength) {
        this(questionId, answerLength, 0, maxLength);
    }
}
