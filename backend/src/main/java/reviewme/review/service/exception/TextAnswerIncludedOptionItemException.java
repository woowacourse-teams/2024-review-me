package reviewme.review.service.exception;

import reviewme.global.exception.BadRequestException;

public class TextAnswerIncludedOptionItemException extends BadRequestException {

    public TextAnswerIncludedOptionItemException() {
        super("텍스트형 응답은 옵션 항목을 포함할 수 없어요.", "Text type answer cannot have option items");
    }
}
