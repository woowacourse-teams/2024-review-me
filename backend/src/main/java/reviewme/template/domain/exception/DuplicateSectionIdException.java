package reviewme.template.domain.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class DuplicateSectionIdException extends BadRequestException {

    public DuplicateSectionIdException(List<Long> sectionIds) {
        super("템플릿에는 중복된 섹션이 있을 수 없어요.");
        log.info("Duplicate section ID - sectionIds: {}", sectionIds);
    }
}
