package reviewme.template.domain.exception;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class StructuredTemplateQuestionValidationException extends BadRequestException {

    public StructuredTemplateQuestionValidationException(Collection<Long> questionIdsOfSections,
                                                         Collection<Long> questionIds) {
        super("서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.");
        log.warn("Question validation failed during StructuredTemplate creation: questionIdsOfSections: {}, questionIds: {}",
                questionIdsOfSections, questionIds);
    }
}
