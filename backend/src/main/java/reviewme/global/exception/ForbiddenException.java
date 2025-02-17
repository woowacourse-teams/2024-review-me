package reviewme.global.exception;

public abstract class ForbiddenException extends ReviewMeException {

    public ForbiddenException(String errorMessage) {
        super(errorMessage);
    }
}
