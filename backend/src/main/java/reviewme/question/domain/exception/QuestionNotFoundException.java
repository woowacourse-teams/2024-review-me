package reviewme.question.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class QuestionNotFoundException extends NotFoundException {

    public QuestionNotFoundException(long questionId) {
        super("질문이 존재하지 않아요.");
        log.warn("Question not found - questionId: {}", questionId, this);
    }
}
