package reviewme.global.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TooManyRequestException extends ReviewMeException {

    public TooManyRequestException(String requestKey) {
        super("짧은 시간 안에 너무 많은 동일한 요청이 일어났어요");
        log.warn("Too many request received - request: {}", requestKey);
    }
}
