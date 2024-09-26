package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.UnauthorizedException;

@Slf4j
public class ReviewGroupUnauthorizedException extends UnauthorizedException {

    public ReviewGroupUnauthorizedException(long reviewGroupId) {
        // 확인 코드가 올바르지 않은 경우에만 해당 예외를 처리하나, 보안상 사용자에게 정확한 이유를 알려주지 않습니다
        super("존재하지 않는 리뷰 그룹이거나, 확인 코드가 올바르지 않아요.");
        log.info("Group access code mismatch on review group: {}", reviewGroupId);
    }
}
