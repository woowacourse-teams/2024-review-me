package reviewme.review.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class ReviewNotFoundException extends NotFoundException {

    public ReviewNotFoundException(long id) {
        super("리뷰가 존재하지 않아요.");
        log.info("ReviewNotFoundException is occurred - id: {}", id);
    }
}
