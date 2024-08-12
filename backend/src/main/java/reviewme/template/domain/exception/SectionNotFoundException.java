package reviewme.template.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class SectionNotFoundException extends NotFoundException {

    public SectionNotFoundException(long id) {
        super("섹션이 존재하지 않아요.");
        log.info("SectionNotFoundException is occurred - id: {}", id);
    }
}
