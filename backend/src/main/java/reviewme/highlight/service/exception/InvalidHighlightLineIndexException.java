package reviewme.highlight.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class InvalidHighlightLineIndexException extends BadRequestException {

    public InvalidHighlightLineIndexException(int submittedLineIndex, int providedMaxLineIndex) {
        super("하이라이트 위치가 답변의 라인을 벗어났어요.");
        log.info("Line index is out of bound - maxIndex: {}, submittedLineIndex: {}", providedMaxLineIndex,
                submittedLineIndex);
    }
}
