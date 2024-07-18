package reviewme.keyword.domain.exception;

import reviewme.global.exception.BadRequestException;

public class DuplicatedKeywordException extends BadRequestException {

    public DuplicatedKeywordException() {
        super("중복된 키워드를 입력했습니다.");
    }
}
