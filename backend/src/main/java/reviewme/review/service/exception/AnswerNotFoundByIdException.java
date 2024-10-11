package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class AnswerNotFoundByIdException extends NotFoundException {

    public AnswerNotFoundByIdException(long answerId) {
        super("답변을 찾을 수 없어요.");
        log.info("Answer not found by id - answerId: {}", answerId);
    }
}
