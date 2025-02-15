package reviewme.global.authorization.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReviewGroupNotExistsBySessionReviewRequestCodeException extends IllegalStateException {

    public ReviewGroupNotExistsBySessionReviewRequestCodeException(String reviewRequestCode) {
        super("서버 내부 에러가 발생했어요.");
        log.error("Review group referenced by request code in session does not exist in database - "
                + "reviewRequestCode: {}", reviewRequestCode);
    }
}
