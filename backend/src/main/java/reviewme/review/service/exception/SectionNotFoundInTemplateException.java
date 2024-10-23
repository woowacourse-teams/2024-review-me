package reviewme.review.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class SectionNotFoundInTemplateException extends NotFoundException {

    public SectionNotFoundInTemplateException(long sectionId, long templateId) {
        super("섹션 정보를 찾을 수 없습니다.");
        log.info("Section not found in template - sectionId: {}, templateId: {}", sectionId, templateId, this);
    }
}
