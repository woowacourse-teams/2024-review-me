package reviewme.question.domain.exception;

import reviewme.global.exception.BadRequestException;

public class DuplicateQuestionException extends BadRequestException {

    public DuplicateQuestionException() {
        super("질문은 중복될 수 없어요.");
    }
}
