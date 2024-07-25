package reviewme.member.domain.exception;

import reviewme.global.exception.BadRequestException;

public class EmptyReviewerException extends BadRequestException {

    public EmptyReviewerException() {
        super("리뷰어는 최소 한 명 이상이어야 합니다.");
    }
}
