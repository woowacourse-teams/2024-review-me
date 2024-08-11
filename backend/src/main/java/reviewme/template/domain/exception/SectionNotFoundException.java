package reviewme.template.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class SectionNotFoundException extends NotFoundException {

    public SectionNotFoundException(long id) {
        super("서버 내부에서 문제가 발생했어요. 서버에 문의해주세요.");
        log.warn("SectionNotFoundException is occurred - id: {}", id);
    }
}
