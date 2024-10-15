package reviewme.highlight.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import java.util.Set;
import org.junit.jupiter.api.Test;
import reviewme.highlight.domain.exception.HighlightIndexExceedLineLengthException;
import reviewme.highlight.entity.HighlightRange;

class HighlightedLineTest {

    @Test
    void 하이라이트_대상_라인의_글자수보다_큰_시작_종료_인덱스_범위를_추가하려고_하면_예외를_발생한다() {
        // given
        String content = "12345";
        HighlightedLine highlightedLine = new HighlightedLine(1, content);

        // when && then
        assertThatCode(() -> highlightedLine.addRange(content.length() - 1, content.length()))
                .isInstanceOf(HighlightIndexExceedLineLengthException.class);
    }

    @Test
    void 하이라이트_할_라인의_시작_종료_인덱스_범위를_추가한다() {
        // given
        HighlightedLine highlightedLine = new HighlightedLine(1, "12345");

        // when
        highlightedLine.addRange(2, 4);
        highlightedLine.addRange(0, 1);

        // then
        Set<HighlightRange> ranges = highlightedLine.getRanges();
        assertThat(ranges).containsExactly(new HighlightRange(2, 4), new HighlightRange(0, 1));
    }
}
