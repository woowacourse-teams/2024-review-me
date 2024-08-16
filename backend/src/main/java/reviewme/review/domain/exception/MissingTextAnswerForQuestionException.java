package reviewme.review.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class MissingTextAnswerForQuestionException extends NotFoundException {

    public MissingTextAnswerForQuestionException(long questionId) {
        super("질문에 해당하는 서술형 답변을 찾지 못했어요.");
        log.error("Text Answer not found for questionId: {}", questionId);
    }
}
