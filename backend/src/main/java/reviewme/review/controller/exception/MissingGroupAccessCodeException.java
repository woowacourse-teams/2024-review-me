package reviewme.review.controller.exception;

import reviewme.global.exception.BadRequestException;

public class MissingGroupAccessCodeException extends BadRequestException {

    public MissingGroupAccessCodeException() {
        super("요청에 확인 코드가 존재하지 않아요.");
    }
}
