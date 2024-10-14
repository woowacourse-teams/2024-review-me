package reviewme.highlight.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;
import reviewme.highlight.domain.exception.HighlightStartIndexExceedEndIndexException;
import reviewme.highlight.domain.exception.NegativeHighlightIndexRangeException;

class HighlightRangeTest {

    @Test
    void 하이라이트의_시작_인덱스가_종료_인덱스보다_큰_경우_예외를_발생한다() {
        assertThatCode(() -> new HighlightRange(2, 1))
                .isInstanceOf(HighlightStartIndexExceedEndIndexException.class);
    }

    @Test
    void 하이라이트의_인덱스들이_0보다_작은_경우_예외를_발생한다() {
        assertThatCode(() -> new HighlightRange(-2, -1))
                .isInstanceOf(NegativeHighlightIndexRangeException.class);
    }
}
