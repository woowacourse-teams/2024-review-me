package reviewme.review.domain.exception;

import reviewme.global.exception.BadRequestException;

public class InvalidRevieweeNameLengthException extends BadRequestException {

    public InvalidRevieweeNameLengthException(int revieweeNameLength, int minLength, int maxLength) {
        super("리뷰이 이름은 %d글자 이상 %d글자 이하여야 해요.".formatted(minLength, maxLength),
                "RevieweeName is out of bound - revieweeNameLength:%d, minLength:%d, maxLength: %d"
                        .formatted(revieweeNameLength, minLength, maxLength)
        );
    }
}
