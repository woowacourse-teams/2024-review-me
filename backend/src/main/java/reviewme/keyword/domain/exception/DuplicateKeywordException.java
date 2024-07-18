package reviewme.keyword.domain.exception;

import reviewme.global.exception.BadRequestException;

public class DuplicateKeywordException extends BadRequestException {

    public DuplicateKeywordException() {
        super("키워드는 중복되지 않게 선택해 주세요.");
    }
}
