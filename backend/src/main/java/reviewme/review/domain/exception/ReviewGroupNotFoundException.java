package reviewme.review.domain.exception;

import reviewme.global.exception.NotFoundException;

public class ReviewGroupNotFoundException extends NotFoundException {

    public ReviewGroupNotFoundException() {
        super("리뷰 그룹을 찾을 수 없어요.");
    }
}
