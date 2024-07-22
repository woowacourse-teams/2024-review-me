package reviewme.review.exception;

import reviewme.global.exception.NotFoundException;

public class ReviewNotFoundException extends NotFoundException {

    public ReviewNotFoundException() {
        super("리뷰가 존재하지 않습니다.");
    }
}
