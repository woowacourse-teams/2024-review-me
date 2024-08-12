package reviewme.template.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class TemplateNotFoundException extends NotFoundException {

    public TemplateNotFoundException(long id) {
        super("템플릿이 존재하지 않아요.");
        log.info("TemplateNotFoundException is occurred - id: {}", id);
    }
}
