package reviewme.global.authorization.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.UnauthorizedException;

@Slf4j
public class UnauthorizedReviewGroupAccessException extends UnauthorizedException {

    public UnauthorizedReviewGroupAccessException() {
        super("리뷰 그룹에 접근할 권한이 없어요.");
        log.info("Unauthorized review group access occurred.");
    }
}
