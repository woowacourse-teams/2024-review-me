package reviewme.review.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class ReviewIsNotInReviewGroupException extends BadRequestException {

    public ReviewIsNotInReviewGroupException(long reviewId, long reviewGroupId) {
        super("리뷰 그룹에 해당하는 리뷰가 아니에요.");
        log.info("Review is not in review group - reviewId: {},reviewGroupId: {}", reviewId, reviewGroupId);
    }
}
