package reviewme.highlight.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class InvalidHighlightLineIndexException extends BadRequestException {

    public InvalidHighlightLineIndexException(long submittedLineIndex, long providedMaxLineIndex) {
        super("줄 번호는 %d 이하여야해요.".formatted(providedMaxLineIndex));
        log.info("Line index is out of bound - maxIndex: {}, submittedLineIndex: {}", providedMaxLineIndex,
                submittedLineIndex);
    }
}
