package reviewme.highlight.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class HighlightIndexExceedLineLengthException extends BadRequestException {

    public HighlightIndexExceedLineLengthException(int lineLength, long startIndex, long endIndex) {
        super("하이라이트 위치는 줄의 글자 수 이내이어야 해요.");
        log.info("Highlight index exceed line length - lineLength: {}, startIndex: {}, endIndex: {}",
                lineLength, startIndex, endIndex);
    }
}
