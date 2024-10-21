package reviewme.highlight.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.highlight.domain.Highlight;
import reviewme.highlight.domain.HighlightRange;

@DataJpaTest
class HighlightRepositoryTest {

    @Autowired
    private HighlightRepository highlightRepository;

    @Test
    void 하이라이트를_줄번호_시작_인덱스_순서대로_정렬해서_가져온다() {
        // given
        highlightRepository.saveAll(
                List.of(
                        new Highlight(1L, 1, new HighlightRange(1, 2)),
                        new Highlight(1L, 2, new HighlightRange(6, 7)),
                        new Highlight(1L, 2, new HighlightRange(2, 3)),
                        new Highlight(1L, 3, new HighlightRange(3, 4)),
                        new Highlight(1L, 1, new HighlightRange(4, 5)),
                        new Highlight(2L, 3, new HighlightRange(7, 8))
                )
        );
        // 1: (1, 2), (4, 5) 2: (2, 3), (6, 7) 3: (3, 4) -> 1 4 2 6 3

        // when
        List<Highlight> actual = highlightRepository.findAllByAnswerIdsOrderedAsc(List.of(1L));

        // then
        assertAll(
                () -> assertThat(actual).extracting(Highlight::getLineIndex)
                        .containsExactly(1, 1, 2, 2, 3),
                () -> assertThat(actual)
                        .extracting(Highlight::getHighlightRange)
                        .extracting(HighlightRange::getStartIndex)
                        .containsExactly(1, 4, 2, 6, 3)
        );
    }
}
