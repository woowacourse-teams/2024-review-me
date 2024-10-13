package reviewme.highlight.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import reviewme.highlight.service.exception.InvalidHighlightLineIndexException;
import reviewme.review.domain.TextAnswer;

class HighlightLinesTest {

    @Test
    void 답변과_하이라이트_할_라인의_인덱스로_하이라이트_할_라인을_생성한다() {
        // given
        TextAnswer answer = new TextAnswer(1L, 1L, "123\n456\n789");

        // when
        HighlightLines highlightLines = new HighlightLines(answer, List.of(0, 2));

        // then
        assertThat(highlightLines.getLines()).containsExactly(
                new HighlightLine(0, "123"),
                new HighlightLine(2, "789")
        );
    }

    @Test
    void 하이라이트_할_라인의_인덱스가_대상_답변의_라인_수를_넘으면_예외를_발생한다() {
        // given
        TextAnswer answer = new TextAnswer(1L, 1L, "123\n456");

        // when && then
        assertThatCode(() -> new HighlightLines(answer, List.of(1, 3)))
                .isInstanceOf(InvalidHighlightLineIndexException.class);
    }
}
