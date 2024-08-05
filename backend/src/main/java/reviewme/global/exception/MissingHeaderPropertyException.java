package reviewme.global.exception;

public class MissingHeaderPropertyException extends BadRequestException {

    public MissingHeaderPropertyException() {
        super("요청에 확인 코드가 존재하지 않아요.");
    }
}
