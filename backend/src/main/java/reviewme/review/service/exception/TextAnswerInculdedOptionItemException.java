package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class TextAnswerInculdedOptionItemException extends BadRequestException {

    public TextAnswerInculdedOptionItemException() {
        super("텍스트형 응답은 옵션 항목을 포함할 수 없어요.");
        log.warn("Text type answer cannot have option items");
    }
}
