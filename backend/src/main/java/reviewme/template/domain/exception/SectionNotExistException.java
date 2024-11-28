package reviewme.template.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class SectionNotExistException extends BadRequestException {

    public SectionNotExistException() {
        super("서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.");
        log.info("No section exists while creating Sections");
    }
}
