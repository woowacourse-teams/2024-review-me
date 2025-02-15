package reviewme.security.aspect.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.ForbiddenException;

@Slf4j
public class ForbiddenReviewAccessException extends ForbiddenException {

    public ForbiddenReviewAccessException() {
        super("리뷰에 접근할 권한이 없어요.");
        log.info("Forbidden review access occurred.");
    }
}
