package reviewme.highlight.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class InvalidHighlightLineIndexException extends BadRequestException {

    public InvalidHighlightLineIndexException(long submittedLineIndex, long maxLineIndex) {
        super("줄 번호는 %d 이하여야해요.".formatted(maxLineIndex));
        log.info("Line index is out of bound - maxIndex: {}, submittedLineIndex: {}", maxLineIndex, submittedLineIndex);
    }
}
