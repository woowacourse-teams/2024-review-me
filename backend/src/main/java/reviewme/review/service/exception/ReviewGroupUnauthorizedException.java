package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.UnauthorizedException;

@Slf4j
public class ReviewGroupUnauthorizedException extends UnauthorizedException {

    public ReviewGroupUnauthorizedException(long reviewGroupId) {
        super("리뷰를 확인할 권한이 없어요.");
        log.info("Group access code mismatch on review group: {}", reviewGroupId);
    }
}
