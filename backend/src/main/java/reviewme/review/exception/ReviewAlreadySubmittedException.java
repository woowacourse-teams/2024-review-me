package reviewme.review.exception;

import reviewme.global.exception.BadRequestException;

public class ReviewAlreadySubmittedException extends BadRequestException {

    public ReviewAlreadySubmittedException() {
        super("이미 리뷰를 작성한 경우 리뷰를 작성할 수 없습니다.");
    }
}
