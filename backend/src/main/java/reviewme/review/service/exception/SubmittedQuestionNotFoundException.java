package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class SubmittedQuestionNotFoundException extends NotFoundException {

    public SubmittedQuestionNotFoundException(long questionId) {
        super("제출된 질문이 존재하지 않아요.", "Submitted question not found - questionId: %d".formatted(questionId));
    }
}
