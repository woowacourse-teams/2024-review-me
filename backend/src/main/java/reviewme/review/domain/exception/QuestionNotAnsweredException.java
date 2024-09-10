package reviewme.review.domain.exception;

import reviewme.global.exception.BadRequestException;

public class QuestionNotAnsweredException extends BadRequestException {

    public QuestionNotAnsweredException(long questionId) {
        super("질문에 대한 답변을 작성하지 않았어요.",
                "question must be answered - questionId: %d".formatted(questionId));
    }
}
