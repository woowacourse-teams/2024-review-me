package reviewme.template.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class SectionTest {

    @Test
    void 조건_옵션을_선택하면_섹션이_보인다() {
        // given
        Section section = new Section(VisibleType.CONDITIONAL, List.of(), 1L, "1", 1);

        // when
        boolean actual = section.isVisibleBySelectedOptionIds(List.of(1L, 2L, 3L));

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 조건_옵션을_선택하지_않으면_섹션이_보이지_않는다() {
        // given
        Section section = new Section(VisibleType.CONDITIONAL, List.of(), 1L, "1", 1);

        // when
        boolean actual = section.isVisibleBySelectedOptionIds(List.of(4L, 5L, 6L));

        // then
        assertThat(actual).isFalse();
    }

    @Test
    void 타입이_ALWAYS라면_조건과_상관없이_모두_보인다() {
        // given
        Section section = new Section(VisibleType.ALWAYS, List.of(), null, "1", 1);

        // when
        boolean actual = section.isVisibleBySelectedOptionIds(List.of());

        // then
        assertThat(actual).isTrue();
    }
}
