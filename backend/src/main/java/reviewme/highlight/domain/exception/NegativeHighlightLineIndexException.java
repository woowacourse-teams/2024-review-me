package reviewme.highlight.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class NegativeHighlightLineIndexException extends BadRequestException {

    public NegativeHighlightLineIndexException(int lineIndex) {
        super("하이라이트 할 라인의 위치는 0 이상의 수이어야 해요.");
        log.info("Highlight index is a negative number - lineIndex: {}", lineIndex);
    }
}
