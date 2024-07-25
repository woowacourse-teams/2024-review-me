package reviewme.review.domain.exception;

import reviewme.global.exception.BadRequestException;

public class InvalidAnswerLengthException extends BadRequestException {

    public InvalidAnswerLengthException(int minLength, int maxLength) {
        super("답변의 길이는 %d자 이상 %d자 이하여야 합니다.".formatted(minLength, maxLength));
    }
}
