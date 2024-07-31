package reviewme.reviewgroup.domain.exception;

import reviewme.global.exception.BadRequestException;

public class InvalidReviewRequestCodeException extends BadRequestException {

    public InvalidReviewRequestCodeException() {
        super("잘못된 리뷰 요청코드입니다.");
    }
}
