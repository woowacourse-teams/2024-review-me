package reviewme.security.aspect.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpELEvaluationFailedException extends IllegalStateException {

    public SpELEvaluationFailedException(String methodName, String expression) {
        super("서버 내부 에러가 발생했어요.");
        log.error("SpEL evaluation failed - method: {}, expression: {}", methodName, expression);
    }
}
