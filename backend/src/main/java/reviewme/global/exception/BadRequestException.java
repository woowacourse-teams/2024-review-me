package reviewme.global.exception;

public abstract class BadRequestException extends ReviewMeException {

    protected BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
