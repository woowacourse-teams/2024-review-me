package reviewme.template.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.DataInconsistencyException;

@Slf4j
public class QuestionInSectionNotFoundException extends DataInconsistencyException {

    public QuestionInSectionNotFoundException(long sectionId, long questionId) {
        super("섹션에 질문이 존재하지 않아요.");
        log.error("Question in section not found - sectionId: {}, questionId: {}", sectionId, questionId);
    }
}
