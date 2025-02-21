package reviewme.template.domain;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class EmptyOptionGroupException extends BadRequestException {

    public EmptyOptionGroupException() {
        super("옵션 아이템은 최소 한 개 이상이어야 해요.");
        log.info("OptionItems were empty while creating Option Group.");
    }
}
