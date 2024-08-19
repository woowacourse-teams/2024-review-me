package reviewme.review.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class MissingTextAnswerForQuestionException extends NotFoundException {

    public MissingTextAnswerForQuestionException(long questionId) {
        super("서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.");
        log.error("Text Answer not found for questionId: {}", questionId);
    }
}
