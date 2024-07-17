package reviewme.member.domain.exception;

import reviewme.global.exception.BadRequestException;

public class InvalidGroupNameLengthException extends BadRequestException {

    public InvalidGroupNameLengthException(int maxLength) {
        super("리뷰 그룹 이름은 1글자 이상 %d글자 이하여야 합니다.".formatted(maxLength));
    }
}
