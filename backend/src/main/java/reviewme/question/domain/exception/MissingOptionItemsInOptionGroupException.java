package reviewme.question.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class MissingOptionItemsInOptionGroupException extends NotFoundException {

    public MissingOptionItemsInOptionGroupException(long optionGroupId) {
        super("서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.");
        log.error("While look up detailed review, DataConsistencyError occurred. "
                + "OptionGroup has no OptionItems - optionGroupId: {}", optionGroupId);
    }
}
