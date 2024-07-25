package reviewme.keyword.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static reviewme.fixture.KeywordFixture.꼼꼼하게_기록해요;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import reviewme.keyword.domain.exception.DuplicateKeywordException;
import reviewme.keyword.domain.exception.KeywordLimitExceedException;

class KeywordsTest {

    @Test
    void 키워드는_최대_5개만_선택할_수_있다() {
        // given
        List<Keyword> keywords = Stream.of("1", "2", "3", "4", "5")
                .map(Keyword::new)
                .toList();

        // when, then
        assertDoesNotThrow(() -> new Keywords(keywords));
    }

    @Test
    void 키워드는_5개를_초과해서_선택할_수_없다() {
        // given
        List<Keyword> keywords = Stream.of("1", "2", "3", "4", "5", "6")
                .map(Keyword::new)
                .toList();

        // when, then
        assertThatThrownBy(() -> new Keywords(keywords))
                .isInstanceOf(KeywordLimitExceedException.class);
    }

    @Test
    void 키워드는_중복으로_선택할_수_없다() {
        // given
        List<Keyword> keywords = List.of(
                꼼꼼하게_기록해요.create(),
                꼼꼼하게_기록해요.create()
        );

        // when, then
        assertThatThrownBy(() -> new Keywords(keywords))
                .isInstanceOf(DuplicateKeywordException.class);
    }
}
