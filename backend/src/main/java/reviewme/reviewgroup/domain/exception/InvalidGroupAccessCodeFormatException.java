package reviewme.reviewgroup.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class InvalidGroupAccessCodeFormatException extends BadRequestException {

    public InvalidGroupAccessCodeFormatException(String groupAccessCode) {
        super("그룹 액세스 코드 형식이 올바르지 않아요.");
        log.info("Invalid groupAccessCode format - groupAccessCode: {}", groupAccessCode);
    }
}
