package reviewme.review.domain.exception;

import reviewme.global.exception.BadRequestException;

public class InvalidRevieweeNameLengthException extends BadRequestException {

    public InvalidRevieweeNameLengthException(int maxLength) {
        super("리뷰이 이름은 1글자 이상 %d글자 이하여야 합니다.".formatted(maxLength));
    }
}
