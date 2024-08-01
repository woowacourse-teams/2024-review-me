package reviewme.review.domain.exception;

import reviewme.global.exception.BadRequestException;

public class ReviewIsNotInReviewGroupException extends BadRequestException {

    public ReviewIsNotInReviewGroupException() {
        super("리뷰 그룹에 해당하는 리뷰가 아니에요.");
    }
}
