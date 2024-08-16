package reviewme.global.exception;

public abstract class DataConsistencyException extends ReviewMeException {

    protected DataConsistencyException(String errorMessage) {
        super(errorMessage);
    }
}
