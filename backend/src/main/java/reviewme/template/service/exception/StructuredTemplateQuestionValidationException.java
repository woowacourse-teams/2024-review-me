package reviewme.template.service.exception;

import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class StructuredTemplateQuestionValidationException extends BadRequestException {

    public StructuredTemplateQuestionValidationException(Set<Long> questionIdsOfSections, List<Long> questionIds) {
        super("서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.");
        log.warn("Question validation failed during StructuredTemplate creation: questionIdsOfSections: {}, questionIds: {}",
                questionIdsOfSections, questionIds);
    }
}
