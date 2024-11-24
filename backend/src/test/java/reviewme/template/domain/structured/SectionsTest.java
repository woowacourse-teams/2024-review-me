package reviewme.template.domain.structured;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static reviewme.fixture.SectionFixture.조건부로_보이는_섹션;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.exception.SectionNotExistException;
import reviewme.template.repository.SectionRepository;

@ServiceTest
class SectionsTest {

    @Autowired
    private SectionRepository sectionRepository;

    @Test
    void 존재하지_않는_섹션으로_생성할_경우_예외가_발생한다() {
        // given, when, then
        assertAll(
                () -> assertThatThrownBy(() -> new Sections(null)).isInstanceOf(SectionNotExistException.class),
                () -> assertThatThrownBy(() -> new Sections(List.of())).isInstanceOf(SectionNotExistException.class)
        );
    }

    @Test
    void 섹션ID들을_반환한다() {
        // given
        Section section1 = sectionRepository.save(항상_보이는_섹션(List.of(1L)));
        Section section2 = sectionRepository.save(항상_보이는_섹션(List.of(1L)));
        Section section3 = sectionRepository.save(항상_보이는_섹션(List.of(1L)));

        Sections sections = new Sections(List.of(section1, section2, section3));

        // when
        List<Long> actual = sections.getSectionIds();

        // then
        assertThat(actual).containsExactlyInAnyOrder(section1.getId(),section2.getId(), section3.getId());
    }

    @Test
    void 섹션의_질문ID들을_반환한다() {
        // given
        long questionId1 = 1L;
        long questionId2 = 2L;
        Section section1 = sectionRepository.save(항상_보이는_섹션(List.of(questionId1)));
        Section section2 = sectionRepository.save(항상_보이는_섹션(List.of(questionId2)));
        Section section3 = sectionRepository.save(항상_보이는_섹션(List.of(questionId2)));

        Sections sections = new Sections(List.of(section1, section2, section3));

        // when
        Set<Long> actual = sections.getQuestionIds();

        // then
        assertThat(actual).containsExactlyInAnyOrder(questionId1, questionId2);
    }

    @Test
    void 선택형_옵션들이_주어질때_노출되지_않는_섹션의_ID를_반환한다() {
        // given
        long optionItemId1 = 1L;
        long optionItemId2 = 2L;
        Section alwaysVisibleSection = sectionRepository.save(항상_보이는_섹션(List.of(1L)));
        Section conditionalSection1 = sectionRepository.save(조건부로_보이는_섹션(List.of(1L), optionItemId1));
        Section conditionalSection2 = sectionRepository.save(조건부로_보이는_섹션(List.of(1L), optionItemId2));

        Sections sections = new Sections(List.of(alwaysVisibleSection, conditionalSection1, conditionalSection2));

        // when
        List<Long> actual = sections.getInvisibleSectionIds(List.of(optionItemId1));

        // then
        assertThat(actual).containsExactlyInAnyOrder(conditionalSection2.getId());
    }

    @Test
    void 선택형_옵션들이_주어지지_않을때_노출되지_않는_섹션의_ID를_반환한다() {
        // given
        long optionItemId1 = 1L;
        long optionItemId2 = 2L;
        Section alwaysVisibleSection = sectionRepository.save(항상_보이는_섹션(List.of(1L)));
        Section conditionalSection1 = sectionRepository.save(조건부로_보이는_섹션(List.of(1L), optionItemId1));
        Section conditionalSection2 = sectionRepository.save(조건부로_보이는_섹션(List.of(1L), optionItemId2));

        Sections sections = new Sections(List.of(alwaysVisibleSection, conditionalSection1, conditionalSection2));

        // when
        List<Long> actual = sections.getInvisibleSectionIds();

        // then
        assertThat(actual).containsExactlyInAnyOrder(conditionalSection1.getId(), conditionalSection2.getId());
    }
}
