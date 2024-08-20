package reviewme.reviewgroup.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.UnAuthorizedException;

@Slf4j
public class ReviewGroupUnAuthorizedException extends UnAuthorizedException {

    public ReviewGroupUnAuthorizedException(String reviewRequestCode) {
        super("리뷰 그룹에 대한 권한이 없어요.");
        log.info("Wrong access code for review group - reviewRequestCode: {}", reviewRequestCode);
    }
}
