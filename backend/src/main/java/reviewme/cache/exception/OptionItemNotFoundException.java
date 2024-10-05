package reviewme.cache.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.DataInconsistencyException;

@Slf4j
public class OptionItemNotFoundException extends DataInconsistencyException {

    public OptionItemNotFoundException(long optionItemId) {
        super("서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.");
        log.error("OptionItem not found - optionItemId: {}", optionItemId, this);
    }
}

