package reviewme.highlight.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import java.util.Set;
import org.junit.jupiter.api.Test;
import reviewme.highlight.domain.exception.HighlightIndexExceedLineLengthException;

class HighlightLineTest {

    @Test
    void 하이라이트_대상_라인의_글자수보다_큰_시작_종료_인덱스_범위를_추가하려고_하면_예외를_발생한다() {
        // given
        String content = "12345";
        HighlightLine highlightLine = new HighlightLine(1, content);

        // when && then
        assertThatCode(() -> highlightLine.setRange(content.length() - 1, content.length()))
                .isInstanceOf(HighlightIndexExceedLineLengthException.class);
    }

    @Test
    void 하이라이트_할_라인의_시작_종료_인덱스_범위를_추가한다() {
        // given
        HighlightLine highlightLine = new HighlightLine(1, "12345");

        // when
        highlightLine.setRange(2, 4);
        highlightLine.setRange(0, 1);

        // then
        Set<HighlightRange> ranges = highlightLine.getRanges();
        assertThat(ranges).containsExactly(new HighlightRange(2, 4), new HighlightRange(0, 1));
    }
}
