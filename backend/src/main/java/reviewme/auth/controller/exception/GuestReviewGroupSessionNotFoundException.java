package reviewme.auth.controller.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class GuestReviewGroupSessionNotFoundException extends BadRequestException {

    public GuestReviewGroupSessionNotFoundException() {
        super("비회원 리뷰 그룹 세션이 존재하지 않아요.");
    }
}
