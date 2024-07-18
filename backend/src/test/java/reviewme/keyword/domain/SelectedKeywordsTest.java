package reviewme.keyword.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static reviewme.fixture.KeywordFixture.꼼꼼하게_기록해요;
import static reviewme.fixture.KeywordFixture.의견을_잘_조율해요;
import static reviewme.fixture.KeywordFixture.추진력이_좋아요;
import static reviewme.fixture.KeywordFixture.회의를_이끌어요;

import java.util.List;
import org.junit.jupiter.api.Test;
import reviewme.keyword.domain.exception.DuplicatedKeywordException;
import reviewme.keyword.domain.exception.KeywordLimitExceedException;

class SelectedKeywordsTest {

    @Test
    void 키워드는_최대_3개만_선택할_수_있다() {
        // given
        List<Keyword> keywords = List.of(
                꼼꼼하게_기록해요.create(),
                의견을_잘_조율해요.create(),
                추진력이_좋아요.create()
        );

        // when, then
        assertDoesNotThrow(() -> new SelectedKeywords(keywords));
    }

    @Test
    void 키워드는_3개를_초과해서_선택할_수_없다() {
        // given
        List<Keyword> keywords = List.of(
                꼼꼼하게_기록해요.create(),
                의견을_잘_조율해요.create(),
                추진력이_좋아요.create(),
                회의를_이끌어요.create()
        );

        // when, then
        assertThatThrownBy(() -> new SelectedKeywords(keywords))
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
        assertThatThrownBy(() -> new SelectedKeywords(keywords))
                .isInstanceOf(DuplicatedKeywordException.class);
    }
}
