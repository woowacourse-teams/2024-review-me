package reviewme.reviewgroup.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class ReviewGroupNotFoundException extends NotFoundException {

    public ReviewGroupNotFoundException(long reviewGroupId) {
        super("리뷰 그룹을 찾을 수 없어요.");
        log.info("Can't find review group - reviewGroupId: {}", reviewGroupId);
    }
}
