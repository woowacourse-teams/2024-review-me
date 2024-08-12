package reviewme.question.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class MissingOptionGroupForQuestionException extends NotFoundException {

    public MissingOptionGroupForQuestionException(long questionId) {
        super("질문에 해당하는 체크박스 그룹을 찾을 수 없어요.");
        log.info("OptionGroup not found for questionId: {}", questionId);
    }
}
