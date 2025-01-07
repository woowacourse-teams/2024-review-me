package reviewme.template.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class SectionTest {

    @Test
    void 조건_옵션을_선택하면_섹션이_보인다() {
        // given
        OptionItem optionItem = new OptionItem("content", 1, OptionType.CATEGORY);
        ReflectionTestUtils.setField(optionItem, "id", 1L);
        Question question = new Question(true, QuestionType.CHECKBOX, "question", null, 1);
        Section section = new Section(VisibleType.CONDITIONAL, List.of(question), optionItem, "name", "header", 1);

        // when, then
        assertThat(section.isVisibleBySelectedOptionIds(List.of(1L, 2L, 3L))).isTrue();
    }

    @Test
    void 조건_옵션을_선택하지_않으면_섹션이_보이지_않는다() {
        // given
        OptionItem optionItem = new OptionItem("content", 1, OptionType.CATEGORY);
        ReflectionTestUtils.setField(optionItem, "id", 1L);
        Question question = new Question(true, QuestionType.CHECKBOX, "question", null, 1);
        Section section = new Section(VisibleType.CONDITIONAL, List.of(question), optionItem, "name", "header", 1);

        // when, then
        assertThat(section.isVisibleBySelectedOptionIds(List.of(2L))).isFalse();
    }

    @Test
    void 타입이_ALWAYS라면_조건과_상관없이_모두_보인다() {
        // given
        Question question = new Question(true, QuestionType.CHECKBOX, "question", null, 1);
        Section section = 항상_보이는_섹션(List.of(question));

        // when, then
        assertThat(section.isVisibleBySelectedOptionIds(List.of())).isTrue();
    }
}
