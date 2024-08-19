package reviewme.global.exception;

public abstract class UnexpectedRequestException extends ReviewMeException {

    protected UnexpectedRequestException(String errorMessage) {
        super(errorMessage);
    }
}
