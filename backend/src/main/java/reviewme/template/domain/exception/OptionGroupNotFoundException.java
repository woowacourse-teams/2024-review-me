package reviewme.template.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class OptionGroupNotFoundException extends NotFoundException {

    public OptionGroupNotFoundException(long id) {
        super("옵션 그룹이 존재하지 않아요.");
        log.info("OptionGroupNotFoundException is occurred - id: {}", id);
    }
}
