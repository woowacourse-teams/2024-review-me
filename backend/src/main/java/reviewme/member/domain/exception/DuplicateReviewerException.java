package reviewme.member.domain.exception;

import reviewme.global.exception.BadRequestException;

public class DuplicateReviewerException extends BadRequestException {

    public DuplicateReviewerException() {
        super("리뷰어는 중복될 수 없습니다.");
    }
}
