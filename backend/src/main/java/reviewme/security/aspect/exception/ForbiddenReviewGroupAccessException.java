package reviewme.security.aspect.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.ForbiddenException;

@Slf4j
public class ForbiddenReviewGroupAccessException extends ForbiddenException {

    public ForbiddenReviewGroupAccessException() {
        super("리뷰 그룹에 접근할 권한이 없어요.");
        log.info("Forbidden review group access occurred.");
    }
}
