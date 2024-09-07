package reviewme.review.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class QuestionNotAnsweredException extends BadRequestException {

    public QuestionNotAnsweredException(long questionId) {
        super("질문에 대한 답변을 작성하지 않았어요.");
        log.warn("question must be answered - questionId: {}", questionId, this);
    }
}
