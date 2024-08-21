package reviewme.template.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.DataInconsistencyException;

@Slf4j
public class SectionInTemplateNotFoundException extends DataInconsistencyException {

    public SectionInTemplateNotFoundException(long templateId, long sectionId) {
        super("서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.");
        log.warn("SectionNotFoundException has occurred - templateId: {}, sectionId: {}", templateId, sectionId);
    }
}
