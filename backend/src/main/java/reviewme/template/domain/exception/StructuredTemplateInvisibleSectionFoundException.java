package reviewme.template.domain.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class StructuredTemplateInvisibleSectionFoundException extends BadRequestException {

    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.";

    public StructuredTemplateInvisibleSectionFoundException(List<Long> invisibleSectionIds, List<Long> optionIds) {
        super(INTERNAL_SERVER_ERROR_MESSAGE);
        logWarning(invisibleSectionIds, optionIds);
    }

    public StructuredTemplateInvisibleSectionFoundException(List<Long> invisibleSectionIds) {
        super(INTERNAL_SERVER_ERROR_MESSAGE);
        logWarning(invisibleSectionIds, null);
    }

    private void logWarning(List<Long> invisibleSectionIds, List<Long> optionIds) {
        if (optionIds != null) {
            log.warn("Sections that cannot be exposed were found during StructuredTemplate creation: invisibleSectionIds: {}, optionIds: {}",
                    invisibleSectionIds, optionIds);
        } else {
            log.warn("Sections that cannot be exposed were found during StructuredTemplate creation: invisibleSectionIds: {}",
                    invisibleSectionIds);
        }
    }
}
