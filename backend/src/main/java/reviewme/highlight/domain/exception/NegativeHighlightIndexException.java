package reviewme.highlight.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class NegativeHighlightIndexException extends BadRequestException {

    public NegativeHighlightIndexException(long startIndex, long endIndex) {
        super("하이라이트 위치는 1 이상의 수이어야 해요.");
        log.info("Highlight index is a negative number - startIndex: {}, endIndex: {}", startIndex, endIndex);
    }
}
