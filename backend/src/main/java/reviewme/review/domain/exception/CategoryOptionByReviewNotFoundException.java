package reviewme.review.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class CategoryOptionByReviewNotFoundException extends NotFoundException {

    public CategoryOptionByReviewNotFoundException(long reviewId) {
        super("리뷰에 선택한 카테고리가 없어요.");
        log.warn("CategoryOptionNotFoundException is occured - reviewId: {}", reviewId, this);
    }
}
