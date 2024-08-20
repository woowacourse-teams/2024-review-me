package reviewme.template.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.DataInconsistencyException;

@Slf4j
public class OptionGroupNotFoundByQuestionIdException extends DataInconsistencyException {

    public OptionGroupNotFoundByQuestionIdException(long questionId) {
        super("응답한 질문과 대응하는 선택형 문항이 존재하지 않아요.");
        log.error("User submitted checkBoxAnswer without provided options - questionId: {}", questionId);
    }
}
