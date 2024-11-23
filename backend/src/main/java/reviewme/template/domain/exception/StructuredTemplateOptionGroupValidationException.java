package reviewme.template.domain.exception;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class StructuredTemplateOptionGroupValidationException extends BadRequestException {

    public StructuredTemplateOptionGroupValidationException(Collection<Long> checkboxQuestionIds,
                                                            Collection<Long> questionIdsOfOptionGroups) {
        super("서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.");
        log.warn("OptionGroup validation failed during StructuredTemplate creation: checkboxQuestionIds: {}, questionIdsOfOptionGroups: {}",
                checkboxQuestionIds, questionIdsOfOptionGroups);
    }
}
