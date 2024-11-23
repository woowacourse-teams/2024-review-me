package reviewme.template.domain.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class DuplicateQuestionIdException extends BadRequestException {

    public DuplicateQuestionIdException(List<Long> questionIds) {
        super("섹션에는 중복된 질문이 있을 수 없어요.");
        log.info("Duplicate question ID found during create Section - questionIds: {}", questionIds);
    }
}
