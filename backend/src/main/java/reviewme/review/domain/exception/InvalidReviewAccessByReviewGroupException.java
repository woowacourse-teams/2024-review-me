package reviewme.review.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class InvalidReviewAccessByReviewGroupException extends BadRequestException {

    public InvalidReviewAccessByReviewGroupException(long reviewId, long reviewGroupId) {
        super("리뷰가 존재하지 않아요.");
        log.info("Review is not in review group - reviewId: {}, reviewGroupId: {}", reviewId, reviewGroupId);
    }
}
