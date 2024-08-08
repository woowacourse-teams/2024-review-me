package reviewme.keyword.domain.exception;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class KeywordsNotFoundException extends NotFoundException {

    public KeywordsNotFoundException(List<Long> selectedKeywordIds) {
        super("키워드가 존재하지 않아요.");
        log.info("Selected keywords not found - selectedKeywordIds {}", selectedKeywordIds);
    }
}
