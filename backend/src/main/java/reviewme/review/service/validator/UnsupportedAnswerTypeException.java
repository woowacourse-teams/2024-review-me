package reviewme.review.service.validator;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.DataInconsistencyException;

@Slf4j
public class UnsupportedAnswerTypeException extends DataInconsistencyException {

    public UnsupportedAnswerTypeException(Class<?> answerClass) {
        super("서버 내부 오류입니다. 관리자에게 문의해주세요.");
        log.error("Unsupported answer class for validation - answerClass: {}", answerClass);
    }
}
