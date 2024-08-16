package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class OptionItemNotFoundBySelectedOptionId extends NotFoundException {

    public OptionItemNotFoundBySelectedOptionId(long selectedOptionId) {
        super("서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.");
        log.error("While look up list of review, DataConsistencyError occurred. "
                + "Submitted checkBox's option item is not exist in database - selectedOptionId: {}", selectedOptionId);
    }
}
