package reviewme.template.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.DataInconsistencyException;

@Slf4j
public class QuestionInSectionNotFoundException extends DataInconsistencyException {

    public QuestionInSectionNotFoundException(long sectionId, long questionId) {
        super("서버 내부에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
        log.error("Question in section not found - sectionId: {}, questionId: {}", sectionId, questionId, this);
    }
}
