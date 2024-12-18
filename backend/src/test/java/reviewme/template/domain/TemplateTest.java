package reviewme.template.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import reviewme.template.domain.exception.DuplicateSectionIdException;
import reviewme.template.domain.exception.SectionIdsNotExistException;

class TemplateTest {

    @Test
    void 템플릿_생성시_섹션ID가_없을_경우_예외가_발생한다() {
        // given, when, then
        assertAll(
                () -> assertThatThrownBy(() -> 템플릿(null)).isInstanceOf(SectionIdsNotExistException.class),
                () -> assertThatThrownBy(() -> 템플릿(List.of())).isInstanceOf(SectionIdsNotExistException.class)
        );
    }

    @Test
    void 템플릿_생성시_중복된_섹션ID가_있을_경우_예외가_발생한다() {
        // given
        List<Long> sectionIds = List.of(1L, 1L, 2L, 3L);

        // when, then
        assertThatThrownBy(() -> 템플릿(sectionIds)).isInstanceOf(DuplicateSectionIdException.class);
    }

    @Test
    void 템플릿의_섹션ID들을_반환한다() {
        // given
        List<Long> sectionIds = List.of(1L, 2L, 3L);
        Template template = 템플릿(sectionIds);

        // when
        List<Long> actual = template.getSectionIds();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(sectionIds);
    }
}
