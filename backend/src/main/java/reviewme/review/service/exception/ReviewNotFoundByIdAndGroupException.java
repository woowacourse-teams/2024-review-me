package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class ReviewNotFoundByIdAndGroupException extends NotFoundException {

    public ReviewNotFoundByIdAndGroupException(long reviewId, long reviewGroupId) {
        super("리뷰를 찾을 수 없어요");
        log.info("Review not found from group - reviewGroupId: {}, reviewId: {}", reviewGroupId, reviewId);
    }
}
