package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class MissingRequiredAnswerException extends BadRequestException {

    public MissingRequiredAnswerException(long questionId) {
        super("필수 질문은 반드시 응답해야 해요.");
        log.warn("Required question must be answered - questionId: {}", questionId, this);
    }
}
