package reviewme.global.exception;

public abstract class NotFoundException extends ReviewMeException {

    protected NotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
