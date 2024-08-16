package reviewme.template.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.DataConsistencyException;

@Slf4j
public class OptionGroupNotFoundByQuestionIdException extends DataConsistencyException {

    public OptionGroupNotFoundByQuestionIdException(long questionId) {
        super("응답한 질문과 대응하는 선택형 문항이 존재하지 않아요.");
        log.error("There is no OptionGroup to question, but user Submitted checkBoxAnswer - questionId: {}", questionId);
    }
}
