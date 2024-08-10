package reviewme.question.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class NoOptionItemsInOptionGroupException extends NotFoundException {

    public NoOptionItemsInOptionGroupException(long optionGroupId) {
        super("서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.");
        log.warn("OptionGroup has no OptionItems - optionGroupId: {}", optionGroupId);
    }
}
