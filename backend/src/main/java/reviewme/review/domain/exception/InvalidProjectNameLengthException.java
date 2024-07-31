package reviewme.review.domain.exception;

import reviewme.global.exception.BadRequestException;

public class InvalidProjectNameLengthException extends BadRequestException {

    public InvalidProjectNameLengthException(int maxLength) {
        super("프로젝트 이름은 1글자 이상 %d글자 이하여야 해요.".formatted(maxLength));
    }
}
