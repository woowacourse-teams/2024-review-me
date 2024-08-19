package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class ReviewGroupNotFoundByCodesException extends BadRequestException {
    public ReviewGroupNotFoundByCodesException(String reviewRequestCode, String groupAccessCode) {
        super("리뷰 그룹 정보와 일치하는 정보가 없어요");
        log.info("");
    }
}
