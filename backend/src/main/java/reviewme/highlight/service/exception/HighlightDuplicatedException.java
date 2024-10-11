package reviewme.highlight.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class HighlightDuplicatedException extends BadRequestException {

    public HighlightDuplicatedException(long answerId, long lineIndex, long startIndex, long endIndex) {
        super("중복된 하이라이트는 생성할 수 없어요.");
        log.info("Highlight is duplicated - answerId: {}, lineIndex: {}, startIndex: {}, endIndex: {}",
                answerId, lineIndex, startIndex, endIndex);
    }
}
