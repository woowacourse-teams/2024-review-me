package reviewme.member.domain.exception;

import reviewme.global.exception.BadRequestException;

public class InvalidDescriptionLengthException extends BadRequestException {

    public InvalidDescriptionLengthException(int maxLength) {
        super("리뷰어 그룹 설명은 %d자 이하로 작성해야 합니다.".formatted(maxLength));
    }
}
