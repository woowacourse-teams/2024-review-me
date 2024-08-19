package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class ReviewGroupNotFoundByCodesException extends BadRequestException {

    public ReviewGroupNotFoundByCodesException(String reviewRequestCode, String groupAccessCode) {
        super("코드에 해당하는 리뷰 그룹이 없어요.");
        log.info("ReviewGroup not found by codes - reviewRequestCode: {}, groupAccessCode: {}",
                reviewRequestCode, groupAccessCode);
    }
}
