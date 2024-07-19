package reviewme.global.exception;

public abstract class UnAuthorizedException extends ReviewMeException {

    protected UnAuthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
