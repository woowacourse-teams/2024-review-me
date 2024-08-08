package reviewme.review.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class ReviewGroupNotFoundByGroupAccessCodeException extends NotFoundException {

    public ReviewGroupNotFoundByGroupAccessCodeException(String groupAccessCode) {
        super("리뷰 그룹을 찾을 수 없어요.");
        log.info("ReviewGroup not found by groupAccessCode - groupAccessCode: {}", groupAccessCode);
    }
}
