package reviewme.review.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class ReviewGroupNotFoundByRequestReviewCodeException extends NotFoundException {

    public ReviewGroupNotFoundByRequestReviewCodeException(String requestReviewCode) {
        super("리뷰 요청 코드에 대한 리뷰 그룹을 찾을 수 없어요.");
        log.info("ReviewGroup not found by requestReviewCode - requestReviewCode: {}", requestReviewCode);
    }
}
