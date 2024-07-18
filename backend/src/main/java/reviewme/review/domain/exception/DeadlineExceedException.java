package reviewme.review.domain.exception;

import reviewme.global.exception.BadRequestException;

public class DeadlineExceedException extends BadRequestException {

    public DeadlineExceedException() {
        super("리뷰 기간이 만료되었습니다.");
    }
}
