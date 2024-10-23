package reviewme.highlight.domain.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class HighlightStartIndexExceedEndIndexException extends BadRequestException {

    public HighlightStartIndexExceedEndIndexException(int startIndex, int endIndex) {
        super("하이라이트 끝 위치는 시작 위치보다 같거나 커야 해요.");
        log.info("Highlight start index exceed end index - startIndex: {}, endIndex: {}", startIndex, endIndex);
    }
}
