package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class ReviewNotFoundException extends NotFoundException {

    public ReviewNotFoundException(String reviewRequestCode, long reviewId) {
        super("리뷰가 존재하지 않아요.");
        log.info("Review not found: reviewRequestCode: {}, reviewId: {}", reviewRequestCode, reviewId);
    }
}
