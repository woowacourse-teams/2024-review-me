package reviewme.highlight.entity;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;
import reviewme.highlight.domain.HighlightRange;
import reviewme.highlight.domain.exception.InvalidHighlightIndexRangeException;

class HighlightRangeTest {

    @Test
    void 하이라이트의_시작_인덱스가_종료_인덱스보다_큰_경우_예외를_발생한다() {
        assertThatCode(() -> new HighlightRange(2, 1))
                .isInstanceOf(InvalidHighlightIndexRangeException.class);
    }

    @Test
    void 하이라이트의_인덱스들이_0보다_작은_경우_예외를_발생한다() {
        assertThatCode(() -> new HighlightRange(-2, -1))
                .isInstanceOf(InvalidHighlightIndexRangeException.class);
    }
}
