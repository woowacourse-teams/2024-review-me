package reviewme.question.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class OptionItemNotFoundException extends NotFoundException {

    public OptionItemNotFoundException(long id) {
        super("선택지가 존재하지 않아요.");
        log.info("OptionItemNotFoundException is occurred - id: {}", id);
    }
}
