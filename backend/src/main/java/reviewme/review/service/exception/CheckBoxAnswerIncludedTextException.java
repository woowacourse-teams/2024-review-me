package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class CheckBoxAnswerIncludedTextException extends BadRequestException {

    public CheckBoxAnswerIncludedTextException(long questionId) {
        super("체크박스형 응답은 텍스트를 포함할 수 없어요.",
                "CheckBox type answer cannot have option items - questionId: %d".formatted(questionId));
    }
}
