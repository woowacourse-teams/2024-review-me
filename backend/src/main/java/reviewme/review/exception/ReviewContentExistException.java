package reviewme.review.exception;

import reviewme.global.exception.BadRequestException;

public class ReviewContentExistException extends BadRequestException {

    public ReviewContentExistException() {
        super("이미 리뷰를 작성한 경우 리뷰를 작성할 수 없습니다.");
    }
}
