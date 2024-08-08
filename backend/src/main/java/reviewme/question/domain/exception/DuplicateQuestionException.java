package reviewme.question.domain.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class DuplicateQuestionException extends BadRequestException {

    public DuplicateQuestionException(List<Long> selectedQuestionIds) {
        super("질문은 중복될 수 없어요.");
        log.info("Selected questions are duplicated - selectedQuestionIds: {}", selectedQuestionIds);
    }
}
