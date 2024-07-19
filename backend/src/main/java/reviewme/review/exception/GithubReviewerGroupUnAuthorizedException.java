package reviewme.review.exception;

import reviewme.global.exception.UnAuthorizedException;

public class GithubReviewerGroupUnAuthorizedException extends UnAuthorizedException {

    public GithubReviewerGroupUnAuthorizedException() {
        super("리뷰어 그룹에 등록되지 않은 github 사용자입니다.");
    }
}
