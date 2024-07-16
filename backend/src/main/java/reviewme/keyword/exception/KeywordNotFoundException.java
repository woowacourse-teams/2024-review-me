package reviewme.keyword.exception;

import reviewme.global.exception.NotFoundException;

public class KeywordNotFoundException extends NotFoundException {

    public KeywordNotFoundException() {
        super("키워드가 존재하지 않습니다.");
    }
}
