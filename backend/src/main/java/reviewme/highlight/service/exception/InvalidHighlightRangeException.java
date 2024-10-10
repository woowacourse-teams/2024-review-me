package reviewme.highlight.service.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.BadRequestException;

@Slf4j
public class InvalidHighlightRangeException extends BadRequestException {

    public InvalidHighlightRangeException(long startIndex, long endIndex) {
        super("하이라이트 시작 글자 인덱스는 종료 글자 인덱스보다 작아야해요.");
        log.info("Highlight index is out of bound - startIndex: {}, endIndex: {}", startIndex, endIndex);
    }
}
