package reviewme.review.domain.exception;

import reviewme.global.exception.BadRequestException;

public class RevieweeMismatchException extends BadRequestException {

    public RevieweeMismatchException() {
        super("리뷰 대상이 일치하지 않습니다.");
    }
}
