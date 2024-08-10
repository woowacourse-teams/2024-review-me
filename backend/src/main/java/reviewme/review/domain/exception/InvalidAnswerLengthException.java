package reviewme.review.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class InvalidAnswerLengthException extends BadRequestException {

    public InvalidAnswerLengthException(int answerLength, int minLength, int maxLength) {
        super("답변의 길이는 %d자 이상 %d자 이하여야 해요.".formatted(minLength, maxLength));
        log.info("AnswerLength is out of bound - answerLength:{}, minLength: {}, maxLength: {}",
                answerLength, minLength, maxLength);
    }
}
