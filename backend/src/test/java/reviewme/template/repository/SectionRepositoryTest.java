package reviewme.template.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;

@DataJpaTest
class SectionRepositoryTest {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Test
    void 템플릿_아이디로_섹션을_불러온다() {
        // given
        List<Long> questionIds = List.of(1L);
        Section section1 = sectionRepository.save(항상_보이는_섹션(questionIds));
        Section section2 = sectionRepository.save(항상_보이는_섹션(questionIds));
        Section section3 = sectionRepository.save(항상_보이는_섹션(questionIds));
        sectionRepository.save(항상_보이는_섹션(questionIds));
        List<Long> sectionIds = List.of(section1.getId(), section2.getId(), section3.getId());

        Template template = templateRepository.save(템플릿(sectionIds));

        // when
        List<Section> actual = sectionRepository.findAllByTemplateId(template.getId());

        // then
        assertThat(actual).containsExactly(section1, section2, section3);
    }

    @Test
    void 템플릿_아이디와_섹션_아이디에_해당하는_섹션이_존재하는지_확인한다() {
        // given
        List<Long> questionIds = List.of(1L);
        Section section1 = sectionRepository.save(항상_보이는_섹션(questionIds));
        Section section2 = sectionRepository.save(항상_보이는_섹션(questionIds));
        Template template = templateRepository.save(템플릿(List.of(section1.getId())));

        // when
        boolean actual1 = sectionRepository.existsByIdAndTemplateId(section1.getId(), template.getId());
        boolean actual2 = sectionRepository.existsByIdAndTemplateId(section2.getId(), template.getId());

        // then
        assertAll(
            () -> assertThat(actual1).isTrue(),
            () -> assertThat(actual2).isFalse()
        );
    }
}
