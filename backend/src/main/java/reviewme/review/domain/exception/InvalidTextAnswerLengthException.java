package reviewme.review.domain.exception;

import reviewme.global.exception.BadRequestException;

public class InvalidTextAnswerLengthException extends BadRequestException {

    public InvalidTextAnswerLengthException(int answerLength, int minLength, int maxLength) {
        super("답변의 길이는 %d자 이상 %d자 이하여야 해요.".formatted(minLength, maxLength),
                "AnswerLength is out of bound - answerLength: %d, minLength: %d, maxLength: %d"
                        .formatted(answerLength, minLength, maxLength));
    }
}
