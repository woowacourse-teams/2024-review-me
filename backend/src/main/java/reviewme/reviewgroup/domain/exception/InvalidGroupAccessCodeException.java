package reviewme.reviewgroup.domain.exception;

import reviewme.global.exception.BadRequestException;

public class InvalidGroupAccessCodeException extends BadRequestException {

    public InvalidGroupAccessCodeException() {
        super("잘못된 그룹 액세스 코드예요.");
    }
}
