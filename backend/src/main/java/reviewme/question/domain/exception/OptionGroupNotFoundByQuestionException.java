package reviewme.question.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class OptionGroupNotFoundByQuestionException extends NotFoundException {

    public OptionGroupNotFoundByQuestionException(long questionId) {
        super("서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.");
        log.info("OptionGroup not found by question - questionId: {}", questionId);
    }
}
