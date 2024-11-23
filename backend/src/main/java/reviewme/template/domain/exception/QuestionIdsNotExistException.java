package reviewme.template.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class QuestionIdsNotExistException extends BadRequestException {

    public QuestionIdsNotExistException() {
        super("섹션에는 하나 이상의 질문이 존재해야 해요.");
        log.info("Not exist question during create Section");
    }
}
