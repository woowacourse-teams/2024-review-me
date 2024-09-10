package reviewme.global.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class NotFoundException extends ReviewMeException {

    protected NotFoundException(String errorMessage, String logMessage) {
        super(errorMessage);
        log.info(logMessage);
    }
}
