package reviewme.review.exception;

import reviewme.global.exception.NotFoundException;

public class GitHubReviewGroupNotFoundException extends NotFoundException {

    public GitHubReviewGroupNotFoundException() {
        super("일치하는 깃헙 사용자와 리뷰어 그룹이 존재하지 않습니다.");
    }
}
