package reviewme.keyword.domain.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class DuplicateKeywordException extends BadRequestException {

    public DuplicateKeywordException(List<Long> selectedKeywordIds) {
        super("키워드는 중복되지 않게 선택해 주세요.");
        log.info("Selected keywords are duplicated - selectedKeywordIds: {}", selectedKeywordIds);
    }
}
