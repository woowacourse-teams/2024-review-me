package reviewme.template.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class SectionIdsNotExistException extends BadRequestException {

    public SectionIdsNotExistException() {
        super("템플릿에는 하나 이상의 섹션이 존재해야 해요.");
        log.info("No section exists while creating Template");
    }
}
