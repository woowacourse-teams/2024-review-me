package reviewme.member.exception;

import reviewme.global.exception.NotFoundException;

public class ReviewerGroupNotFoundException extends NotFoundException {

    public ReviewerGroupNotFoundException() {
        super("리뷰어 그룹이 존재하지 않습니다.");
    }
}
