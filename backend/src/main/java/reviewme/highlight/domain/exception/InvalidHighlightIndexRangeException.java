package reviewme.highlight.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class InvalidHighlightIndexRangeException extends BadRequestException {

    public InvalidHighlightIndexRangeException(int startIndex, int endIndex) {
        super("유효하지 않은 하이라이트 위치에요. 하이라이트 시작 위치: %d, 종료 위치: %d".formatted(startIndex, endIndex));
        log.info("Highlight index is a negative number - startIndex: {}, endIndex: {}", startIndex, endIndex);
    }
}
