package reviewme.global.authorization.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.UnauthorizedException;

@Slf4j
public class UnauthorizedReviewAccessException extends UnauthorizedException {

    public UnauthorizedReviewAccessException() {
        super("리뷰에 접근할 권한이 없어요.");
        log.info("Unauthorized review access occurred.");
    }
}
