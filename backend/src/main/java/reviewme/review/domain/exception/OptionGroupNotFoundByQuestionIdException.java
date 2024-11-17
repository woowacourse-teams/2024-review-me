package reviewme.review.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.DataInconsistencyException;

@Slf4j
public class OptionGroupNotFoundByQuestionIdException extends DataInconsistencyException {

    public OptionGroupNotFoundByQuestionIdException(long questionId) {
        super("서버 내부에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
        log.error("User submitted checkBoxAnswer without provided options - questionId: {}", questionId, this);
    }
}
