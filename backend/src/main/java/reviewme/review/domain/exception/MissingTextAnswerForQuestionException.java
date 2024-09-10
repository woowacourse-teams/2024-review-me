package reviewme.review.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.DataInconsistencyException;

@Slf4j
public class MissingTextAnswerForQuestionException extends DataInconsistencyException {

    public MissingTextAnswerForQuestionException(long questionId) {
        super("서버 내부에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
        log.error("The question is a text question but text answer not found for questionId: {}", questionId, this);
    }
}
