package reviewme.review.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class InvalidRevieweeNameLengthException extends BadRequestException {

    public InvalidRevieweeNameLengthException(int revieweeNameLength, int minLength, int maxLength) {
        super("리뷰이 이름은 %d글자 이상 %d글자 이하여야 해요.".formatted(minLength, maxLength));
        log.info("RevieweeName is out of bound - revieweeNameLength:{}, minLength:{}, maxLength: {}", revieweeNameLength, minLength, maxLength);
    }
}
