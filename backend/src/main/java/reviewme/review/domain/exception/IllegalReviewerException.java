package reviewme.review.domain.exception;

import reviewme.global.exception.BadRequestException;

public class IllegalReviewerException extends BadRequestException {

    public IllegalReviewerException() {
        super("리뷰어와 리뷰이가 같을 수 없습니다.");
    }
}
