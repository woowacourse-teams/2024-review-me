package reviewme.keyword.domain.exception;

import reviewme.global.exception.BadRequestException;

public class KeywordLimitExceedException extends BadRequestException {

    public KeywordLimitExceedException(int minSize, int maxSize) {
        super("키워드는 최소 %d개, 최대 %d개 선택할 수 있어요.".formatted(minSize, maxSize));
    }
}
