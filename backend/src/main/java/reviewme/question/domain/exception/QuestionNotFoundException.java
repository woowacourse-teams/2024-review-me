package reviewme.question.domain.exception;

import reviewme.global.exception.NotFoundException;

public class QuestionNotFoundException extends NotFoundException {

    public QuestionNotFoundException() {
        super("질문이 존재하지 않아요.");
    }
}
