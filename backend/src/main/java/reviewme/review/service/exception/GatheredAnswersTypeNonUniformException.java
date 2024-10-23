package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.DataInconsistencyException;

@Slf4j
public class GatheredAnswersTypeNonUniformException extends DataInconsistencyException {

    public GatheredAnswersTypeNonUniformException(Throwable cause) {
        super("서버 내부 오류가 발생했습니다.");
        log.error("The types of answers to questions are not uniform.", cause);
    }
}
