package reviewme.keyword.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.NotFoundException;

@Slf4j
public class KeywordNotFoundException extends NotFoundException {

    public KeywordNotFoundException(long id) {
        super("키워드가 존재하지 않아요.");
        log.info("Keyword not found by id - id: {}", id);
    }
}
