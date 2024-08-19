package reviewme.global.exception;

public abstract class DataInconsistencyException extends ReviewMeException {

    protected DataInconsistencyException(String errorMessage) {
        super(errorMessage);
    }
}
