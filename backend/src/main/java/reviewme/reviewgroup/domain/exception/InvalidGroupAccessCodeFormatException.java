package reviewme.reviewgroup.domain.exception;

import reviewme.global.exception.BadRequestException;

public class InvalidGroupAccessCodeFormatException extends BadRequestException {

    public InvalidGroupAccessCodeFormatException(String groupAccessCode) {
        super("그룹 액세스 코드 형식이 올바르지 않아요.",
                "Invalid groupAccessCode format - groupAccessCode: %s".formatted(groupAccessCode));
    }
}
