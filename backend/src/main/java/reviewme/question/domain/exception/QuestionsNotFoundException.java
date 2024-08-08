package reviewme.question.domain.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class QuestionsNotFoundException extends NotFoundException {

    public QuestionsNotFoundException(List<Long> selectedQuestionIds) {
        super("질문이 존재하지 않아요.");
        log.info("Selected questions not found - selectedQuestionIds {}", selectedQuestionIds);
    }
}
