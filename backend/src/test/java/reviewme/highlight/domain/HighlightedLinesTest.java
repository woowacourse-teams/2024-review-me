package reviewme.highlight.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.highlight.domain.exception.InvalidHighlightLineIndexException;
import reviewme.highlight.domain.exception.NegativeHighlightLineIndexException;
import reviewme.highlight.entity.HighlightRange;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;

@DataJpaTest
class HighlightedLinesTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    void 답변_내용으로_하이라이트에_사용될_라인을_생성한다() {
        // given
        TextAnswer answer = new TextAnswer(1L, "123\n456\n789");
        reviewRepository.save(new Review(1L, 1L, List.of(answer)));

        // when
        HighlightedLines highlightedLines = new HighlightedLines(answer.getContent());

        // then
        assertThat(highlightedLines.getLines()).containsExactly(
                new HighlightedLine("123"),
                new HighlightedLine("456"),
                new HighlightedLine("789")
        );
    }

    @Test
    void 특정_라인에_하이라이트_시작_종료_범위를_추가한다() {
        // given
        TextAnswer answer = new TextAnswer(1L, "123\n456\n78910");
        reviewRepository.save(new Review(1L, 1L, List.of(answer)));
        HighlightedLines highlightedLines = new HighlightedLines(answer.getContent());

        // when
        highlightedLines.addRange(0, 1, 1);
        highlightedLines.addRange(2, 0, 1);
        highlightedLines.addRange(2, 3, 4);

        // then
        List<HighlightedLine> lines = highlightedLines.getLines();
        assertAll(
                () -> assertThat(lines.get(0).getRanges())
                        .containsExactly(new HighlightRange(1, 1)),
                () -> assertThat(lines.get(2).getRanges())
                        .containsExactly(new HighlightRange(0, 1), new HighlightRange(3, 4))
        );
    }

    @Test
    void 하이라이트에_추가할_라인의_인덱스가_0보다_작을_경우_예외를_발생한다() {
        // given
        HighlightedLines highlightedLines = new HighlightedLines("123\n456");
        int negativeLineIndex = -1;

        // when && then
        assertThatCode(() -> highlightedLines.addRange(negativeLineIndex, 0, 1))
                .isInstanceOf(NegativeHighlightLineIndexException.class);
    }

    @Test
    void 하이라이트에_추가할_라인의_인덱스가_대상_답변의_라인_수를_넘으면_예외를_발생한다() {
        // given
        String content = "123\n456";
        TextAnswer answer = new TextAnswer(1L, content);
        reviewRepository.save(new Review(1L, 1L, List.of(answer)));
        HighlightedLines highlightedLines = new HighlightedLines(answer.getContent());
        int invalidLineIndex = (int) content.lines().count();
        System.out.println(invalidLineIndex);

        // when && then
        assertThatCode(() -> highlightedLines.addRange(invalidLineIndex, 0, 1))
                .isInstanceOf(InvalidHighlightLineIndexException.class);
    }
}
