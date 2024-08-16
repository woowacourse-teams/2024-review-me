package reviewme.review.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class MissingCheckboxAnswerForQuestionException extends NotFoundException {

    public MissingCheckboxAnswerForQuestionException(long questionId) {
        super("질문에 해당하는 선택형 답변을 찾지 못했어요.");
        log.warn("Checkbox Answer not found for questionId: {}", questionId);
    }
}
