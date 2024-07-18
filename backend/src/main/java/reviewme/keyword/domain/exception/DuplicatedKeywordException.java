package reviewme.keyword.domain.exception;

import reviewme.global.exception.BadRequestException;

public class DuplicatedKeywordException extends BadRequestException {

    public DuplicatedKeywordException() {
        super("키워드는 중복되지 않게 선택해 주세요.");
    }
}
