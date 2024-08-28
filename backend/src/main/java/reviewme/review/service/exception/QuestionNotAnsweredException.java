package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class QuestionNotAnsweredException extends BadRequestException {

    public QuestionNotAnsweredException(long answerId) {
        super("답변을 작성하지 않았어요.");
        log.warn("question must be answered - answerId: {}", answerId, this);
    }
}
