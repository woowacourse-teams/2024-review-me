package reviewme.security.resolver.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.UnauthorizedException;

@Slf4j
public class GuestReviewGroupSessionNotExistsException extends UnauthorizedException {

    public GuestReviewGroupSessionNotExistsException() {
        super("인증되지 않은 접근이에요.");
        log.info("Guest ReviewGroup session does not exist.");
    }
}
