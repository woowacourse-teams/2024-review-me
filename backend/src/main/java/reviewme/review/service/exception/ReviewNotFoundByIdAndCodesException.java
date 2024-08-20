package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class ReviewNotFoundByIdAndCodesException extends NotFoundException {

    public ReviewNotFoundByIdAndCodesException(long reviewId, String reviewRequestCode, String groupAccessCode) {
        super("리뷰를 찾을 수 없어요");
        log.info("Review not found - reviewId: {}, reviewRequestCode: {}, groupAccessCode: {}",
                reviewId, reviewRequestCode, groupAccessCode);
    }
}
