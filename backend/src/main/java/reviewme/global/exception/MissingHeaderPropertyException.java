package reviewme.global.exception;

public class MissingHeaderPropertyException extends BadRequestException {

    public MissingHeaderPropertyException(String headerName) {
        super("요청에 %s이(가) 존재하지 않아요.".formatted(headerName));
    }
}
