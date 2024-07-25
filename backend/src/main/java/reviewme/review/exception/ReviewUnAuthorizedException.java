package reviewme.review.exception;

import reviewme.global.exception.UnAuthorizedException;

public class ReviewUnAuthorizedException extends UnAuthorizedException {

    public ReviewUnAuthorizedException() {
        super("리뷰에 대한 권한이 없습니다.");
    }
}
