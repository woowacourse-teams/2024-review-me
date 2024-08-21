package reviewme.global.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class UnexpectedRequestException extends ReviewMeException {

    protected UnexpectedRequestException(String errorMessage) {
        super(errorMessage);
        log.warn("", this);
    }
}
