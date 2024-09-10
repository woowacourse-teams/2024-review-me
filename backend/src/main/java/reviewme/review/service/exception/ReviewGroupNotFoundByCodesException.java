package reviewme.review.service.exception;

import reviewme.global.exception.BadRequestException;

public class ReviewGroupNotFoundByCodesException extends BadRequestException {

    public ReviewGroupNotFoundByCodesException(String reviewRequestCode, String groupAccessCode) {
        super("인증 정보에 해당하는 리뷰 확인 코드와 리뷰 요청 코드를 통해 찾을 수 있는 리뷰 그룹이 없어요.",
                "ReviewGroup not found by codes - reviewRequestCode: %s, groupAccessCode: %s"
                        .formatted(reviewRequestCode, groupAccessCode)
        );
    }
}
