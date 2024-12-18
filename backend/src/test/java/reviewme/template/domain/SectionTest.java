package reviewme.template.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.SectionFixture.조건부로_보이는_섹션;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;

import java.util.List;
import org.junit.jupiter.api.Test;
import reviewme.template.domain.exception.DuplicateQuestionIdException;
import reviewme.template.domain.exception.QuestionIdsNotExistException;

class SectionTest {

    @Test
    void 섹션_생성시_질문ID가_없을_경우_예외가_발생한다() {
        // given, when, then
        assertAll(
                () -> assertThatThrownBy(() -> 항상_보이는_섹션(null)).isInstanceOf(QuestionIdsNotExistException.class),
                () -> assertThatThrownBy(() -> 항상_보이는_섹션(List.of())).isInstanceOf(QuestionIdsNotExistException.class)
        );
    }

    @Test
    void 섹션_생성시_중복된_질문ID가_있을_경우_예외가_발생한다() {
        // given
        List<Long> questionIds = List.of(1L, 1L, 2L, 3L);

        // when, then
        assertThatThrownBy(() -> 항상_보이는_섹션(questionIds))
                .isInstanceOf(DuplicateQuestionIdException.class);
    }

    @Test
    void 섹션의_질문ID들을_반환한다() {
        // given
        List<Long> questionIds = List.of(1L, 2L, 3L);
        Section section = 항상_보이는_섹션(questionIds);

        // when
        List<Long> actual = section.getQuestionIds();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(questionIds);
    }

    @Test
    void 항상_보이는_섹션일_경우_ture를_반환한다() {
        // given
        Section section = 항상_보이는_섹션(List.of(1L));

        // when
        boolean actual = section.isAlwaysVisible();

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 항상_보이는_섹션이_아닐_경우_false를_반환한다() {
        // given
        Section section = 조건부로_보이는_섹션(List.of(1L), 2L);

        // when
        boolean actual = section.isAlwaysVisible();

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void 조건_옵션을_선택하면_섹션이_보인다() {
        // given
        List<Long> questionIds = List.of(1L);
        long optionId1 = 1L;
        long optionId2 = 2L;
        long optionId3 = 3L;

        Section section = 조건부로_보이는_섹션(questionIds, optionId2);

        // when
        boolean actual = section.isVisibleBySelectedOptionIds(List.of(optionId1, optionId2, optionId3));

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 조건_옵션을_선택하지_않으면_섹션이_보이지_않는다() {
        // given
        List<Long> questionIds = List.of(1L);
        long optionId1 = 1L;
        long optionId2 = 2L;
        long optionId3 = 3L;

        Section section = 조건부로_보이는_섹션(questionIds, optionId2);

        // when
        boolean actual = section.isVisibleBySelectedOptionIds(List.of(optionId1, optionId3));

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void 타입이_ALWAYS라면_조건과_상관없이_모두_보인다() {
        // given
        List<Long> questionIds = List.of(1L);
        Section section = 항상_보이는_섹션(questionIds);

        // when
        boolean actual = section.isVisibleBySelectedOptionIds(List.of());

        // then
        assertThat(actual).isTrue();
    }
}
