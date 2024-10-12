package reviewme.reviewgroup.controller;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class ReviewGroupSessionNotFoundException extends BadRequestException {

    public ReviewGroupSessionNotFoundException() {
        super("리뷰 그룹 세션이 존재하지 않습니다.");
    }
}
