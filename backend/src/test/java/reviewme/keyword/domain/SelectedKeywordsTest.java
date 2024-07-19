package reviewme.keyword.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;
import reviewme.keyword.domain.exception.DuplicateKeywordException;
import reviewme.keyword.domain.exception.KeywordLimitExceedException;

class SelectedKeywordsTest {

    @Test
    void 키워드는_최대_5개만_선택할_수_있다() {
        // given
        List<Keyword> keywords = LongStream.rangeClosed(1, 5)
                .mapToObj(id -> new Keyword(id, "Keyword"))
                .toList();

        // when, then
        assertDoesNotThrow(() -> new SelectedKeywords(keywords));
    }

    @Test
    void 키워드는_5개를_초과해서_선택할_수_없다() {
        // given
        List<Keyword> keywords = LongStream.rangeClosed(1, 6)
                .mapToObj(id -> new Keyword(id, "Keyword"))
                .toList();

        // when, then
        assertThatThrownBy(() -> new SelectedKeywords(keywords))
                .isInstanceOf(KeywordLimitExceedException.class);
    }

    @Test
    void 키워드는_중복으로_선택할_수_없다() {
        // given
        List<Keyword> keywords = List.of(
                new Keyword(1L, "꼼꼼해요"),
                new Keyword(1L, "꼼꼼해요")
        );

        // when, then
        assertThatThrownBy(() -> new SelectedKeywords(keywords))
                .isInstanceOf(DuplicateKeywordException.class);
    }
}
