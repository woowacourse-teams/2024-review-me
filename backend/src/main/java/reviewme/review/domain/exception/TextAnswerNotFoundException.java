package reviewme.review.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class TextAnswerNotFoundException extends NotFoundException {

    public TextAnswerNotFoundException(long questionId) {
        super("질문에 해당하는 서술형 답변을 찾지 못했어요.");
        log.warn("Text Answer not found for questionId: {}", questionId);
    }
}
