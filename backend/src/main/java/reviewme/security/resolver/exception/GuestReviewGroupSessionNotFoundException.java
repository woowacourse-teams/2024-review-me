package reviewme.security.resolver.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.UnauthorizedException;

@Slf4j
public class GuestReviewGroupSessionNotFoundException extends UnauthorizedException {

    public GuestReviewGroupSessionNotFoundException() {
        super("인증되지 않은 접근이에요.");
        log.info("Guest ReviewGroup session does not exist.");
    }
}
