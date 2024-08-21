package reviewme.template.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.domain.VisibleType;

@DataJpaTest
class SectionRepositoryTest {

    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private TemplateRepository templateRepository;

    @Test
    void 템플릿_아이디로_섹션을_불러온다() {
        // given
        Section section1 = sectionRepository.save(new Section(VisibleType.ALWAYS, List.of(), null, "1","말머리", 1));
        Section section2 = sectionRepository.save(new Section(VisibleType.ALWAYS, List.of(), null, "2","말머리", 1));
        Section section3 = sectionRepository.save(new Section(VisibleType.ALWAYS, List.of(), null, "3","말머리", 1));
        sectionRepository.save(new Section(VisibleType.ALWAYS, List.of(), null, "4","말머리", 1));
        Template template = templateRepository.save(
                new Template(List.of(section1.getId(), section2.getId(), section3.getId()))
        );

        // when
        List<Section> actual = sectionRepository.findAllByTemplateId(template.getId());

        // then
        assertThat(actual).containsExactly(section1, section2, section3);
    }
}
