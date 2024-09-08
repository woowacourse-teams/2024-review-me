package reviewme.template.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.SectionFixture.조건부로_보이는_섹션;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;

import java.util.List;
import org.junit.jupiter.api.Test;

class SectionTest {

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
