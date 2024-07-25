package reviewme.member.domain.exception;

import reviewme.global.exception.BadRequestException;

public class SelfReviewException extends BadRequestException {

    public SelfReviewException() {
        super("자신을 리뷰어로 추가할 수 없습니다.");
    }
}
