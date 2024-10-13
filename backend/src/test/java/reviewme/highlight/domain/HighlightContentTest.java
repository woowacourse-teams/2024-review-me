package reviewme.highlight.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import reviewme.highlight.service.exception.HighlightDuplicatedException;
import reviewme.review.domain.TextAnswer;

class HighlightContentTest {

    @Test
    void 특정_라인에_하이라이트_시작_종료_범위를_추가한다() {
        // given
        TextAnswer answer = new TextAnswer(1L, 1L, "123\n4567");
        HighlightContent highlightContent = new HighlightContent(answer, List.of(0, 1));

        // when
        highlightContent.addRange(0, 1, 1);
        highlightContent.addRange(1, 0, 1);
        highlightContent.addRange(1, 3, 3);

        // then
        List<HighlightLine> lines = highlightContent.getLines();
        assertAll(
                () -> assertThat(lines.get(0).getRanges()).containsExactly(new HighlightRange(1, 1)),
                () -> assertThat(lines.get(1).getRanges()).containsExactly(new HighlightRange(0, 1), new HighlightRange(3, 3))
        );
    }


    @Test
    void 중복된_하이라이트를_추가하는_경우_예외를_발생한다() {
        // given
        TextAnswer answer = new TextAnswer(1L, 1L, "123\n4567");
        HighlightContent highlightContent = new HighlightContent(answer, List.of(0, 1));

        // when
        highlightContent.addRange(0, 1, 1);

        // then
        assertThatCode(() -> highlightContent.addRange(0, 1, 1))
                .isInstanceOf(HighlightDuplicatedException.class);
    }
}
