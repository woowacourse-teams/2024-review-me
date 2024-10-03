package reviewme.cache.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class QuestionNotFoundException extends NotFoundException {

    public QuestionNotFoundException(long questionId) {
        super("서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.");
        log.error("Question not found - questionId: {}", questionId, this);
    }
}
