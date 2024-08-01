package reviewme.review.service.exception;

import reviewme.global.exception.BadRequestException;

public class InvalidGroupAccessCodeException extends BadRequestException {

    public InvalidGroupAccessCodeException() {
        super("올바르지 않은 확인 코드예요.");
    }
}
