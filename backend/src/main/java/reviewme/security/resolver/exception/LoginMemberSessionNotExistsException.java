package reviewme.security.resolver.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.UnauthorizedException;

@Slf4j
public class LoginMemberSessionNotExistsException extends UnauthorizedException {

    public LoginMemberSessionNotExistsException() {
        super("인증되지 않은 접근이에요.");
        log.info("Login Member session does not exist.");
    }
}
