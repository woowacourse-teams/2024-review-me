package reviewme.question.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.DataInconsistencyException;

@Slf4j
public class MissingOptionItemsInOptionGroupException extends DataInconsistencyException {

    public MissingOptionItemsInOptionGroupException(long optionGroupId) {
        super("서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.");
        log.error("OptionGroup has no OptionItems - optionGroupId: {}", optionGroupId);
    }
}
