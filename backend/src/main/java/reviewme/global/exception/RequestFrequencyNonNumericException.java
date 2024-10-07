package reviewme.global.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestFrequencyNonNumericException extends DataInconsistencyException {

    public RequestFrequencyNonNumericException(Object requestFrequency) {
        super("서버 내부 예외가 발생했어요.");
        log.error("Request frequency is not numeric - actualType: {}", requestFrequency.getClass().getSimpleName());
    }
}
