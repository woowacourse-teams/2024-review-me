package reviewme.global.exception;

public abstract class UnauthorizedException extends ReviewMeException {

    protected UnauthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
