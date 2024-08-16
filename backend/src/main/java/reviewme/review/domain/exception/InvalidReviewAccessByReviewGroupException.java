package reviewme.review.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.UnexpectedRequestException;

@Slf4j
public class InvalidReviewAccessByReviewGroupException extends UnexpectedRequestException {

    public InvalidReviewAccessByReviewGroupException(long reviewId, long reviewGroupId) {
        super("리뷰가 존재하지 않아요.");
        log.warn("Review is not in review group - reviewId: {}, reviewGroupId: {}", reviewId, reviewGroupId);
    }
}
