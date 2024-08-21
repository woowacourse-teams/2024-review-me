package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class RequiredQuestionNotAnsweredException extends BadRequestException {

    public RequiredQuestionNotAnsweredException(long questionId) {
        super("필수 질문의 답변을 작성하지 않았어요.");
        log.warn("Required question must be answered - questionId: {}", questionId, this);
    }
}
