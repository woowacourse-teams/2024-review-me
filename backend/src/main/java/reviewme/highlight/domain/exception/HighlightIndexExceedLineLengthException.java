package reviewme.highlight.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class HighlightIndexExceedLineLengthException extends BadRequestException {

    public HighlightIndexExceedLineLengthException(int lineLength, int startIndex, int endIndex) {
        super("하이라이트 위치가 텍스트의 범위를 벗어났어요.");
        log.info("Highlight index exceed line length - lineLength: {}, startIndex: {}, endIndex: {}",
                lineLength, startIndex, endIndex);
    }
}
