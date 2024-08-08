package reviewme.review.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class InvalidProjectNameLengthException extends BadRequestException {

    public InvalidProjectNameLengthException(int projectNameLength, int minLength, int maxLength) {
        super("프로젝트 이름은 %d글자 이상 %d글자 이하여야 해요.".formatted(minLength, maxLength));
        log.info("ProjectName is out of bound - projectNameLength:{}, minLength:{}, maxLength: {}",
                projectNameLength, minLength, maxLength);
    }
}
