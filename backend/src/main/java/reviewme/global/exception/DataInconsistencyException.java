package reviewme.global.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DataInconsistencyException extends ReviewMeException {

    protected DataInconsistencyException(String errorMessage) {
        super(errorMessage);
    }
}
