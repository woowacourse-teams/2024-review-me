package reviewme.keyword.domain.exception;

import reviewme.global.exception.BadRequestException;

public class KeywordLimitExceedException extends BadRequestException {

    public KeywordLimitExceedException(int maxSize) {
        super("키워드는 최대 %d개 선택할 수 있습니다.".formatted(maxSize));
    }
}
