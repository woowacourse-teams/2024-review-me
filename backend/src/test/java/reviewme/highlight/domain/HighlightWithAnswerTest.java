package reviewme.highlight.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import reviewme.highlight.service.exception.InvalidHighlightLineIndexException;
import reviewme.review.domain.TextAnswer;

class HighlightWithAnswerTest {

    @Test
    void 답변과_하이라이트_할_라인의_인덱스로_하이라이트_할_라인을_생성한다() {
        // given
        TextAnswer answer = new TextAnswer(1L, 1L, "123\n456\n789");

        // when
        HighlightWithAnswer highlightWithAnswer = new HighlightWithAnswer(answer, List.of(0, 2));

        // then
        assertThat(highlightWithAnswer.getLines()).containsExactly(
                new HighlightLine(0, "123"),
                new HighlightLine(2, "789")
        );
    }

    @Test
    void 하이라이트_할_라인의_인덱스가_대상_답변의_라인_수를_넘으면_예외를_발생한다() {
        // given
        TextAnswer answer = new TextAnswer(1L, 1L, "123\n456");

        // when && then
        assertThatCode(() -> new HighlightWithAnswer(answer, List.of(1, 3)))
                .isInstanceOf(InvalidHighlightLineIndexException.class);
    }

    @Test
    void 특정_라인에_하이라이트_시작_종료_범위를_추가한다() {
        // given
        TextAnswer answer = new TextAnswer(1L, 1L, "123\n4567");
        HighlightWithAnswer highlightWithAnswer = new HighlightWithAnswer(answer, List.of(0, 1));

        // when
        highlightWithAnswer.addRange(0, 1, 1);
        highlightWithAnswer.addRange(1, 0, 1);
        highlightWithAnswer.addRange(1, 3, 3);

        // then
        List<HighlightLine> lines = highlightWithAnswer.getLines();
        assertAll(
                () -> assertThat(lines.get(0).getRanges()).containsExactly(new HighlightRange(1, 1)),
                () -> assertThat(lines.get(1).getRanges()).containsExactly(new HighlightRange(0, 1), new HighlightRange(3, 3))
        );
    }
}
