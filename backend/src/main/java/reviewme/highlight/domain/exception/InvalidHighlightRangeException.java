package reviewme.highlight.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class InvalidHighlightRangeException extends BadRequestException {

    public InvalidHighlightRangeException(long startIndex, long endIndex) {
        super("하이라이트 끝 위치는 시작 위치보다 같거나 커야 해요.");
        log.info("Invalid highlight range - startIndex: {}, endIndex: {}", startIndex, endIndex);
    }
}
