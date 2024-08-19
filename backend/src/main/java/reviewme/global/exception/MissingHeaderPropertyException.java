package reviewme.global.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MissingHeaderPropertyException extends BadRequestException {

    public MissingHeaderPropertyException(String headerName) {
        super("요청에 %s이(가) 존재하지 않아요.".formatted(headerName));
        log.info("Missing header property: {}", headerName);
    }
}
