package reviewme.question.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.question.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Test
    void 섹션_아이디로_질문_목록을_순서대로_가져온다() {
        // given
        Question question1 = questionRepository.save(서술형_필수_질문(1));
        Question question2 = questionRepository.save(서술형_필수_질문(2));
        Question question3 = questionRepository.save(서술형_필수_질문(3));
        questionRepository.save(서술형_필수_질문(1));

        List<Long> questionIds = List.of(question3.getId(), question1.getId(), question2.getId());
        Section section = sectionRepository.save(항상_보이는_섹션(questionIds));

        // when
        List<Question> actual = questionRepository.findAllBySectionId(section.getId());

        // then
        assertThat(actual).extracting(Question::getId)
                .containsExactly(question1.getId(), question2.getId(), question3.getId());
    }

    @Test
    void 템플릿_아이디로_질문_목록을_모두_가져온다() {
        // given
        Question question1 = questionRepository.save(서술형_필수_질문(1));
        Question question2 = questionRepository.save(서술형_필수_질문(2));
        Question question3 = questionRepository.save(서술형_필수_질문(1));
        Question question4 = questionRepository.save(서술형_필수_질문(2));

        List<Long> sectionQuestion1 = List.of(question1.getId(), question2.getId());
        List<Long> sectionQuestion2 = List.of(question3.getId(), question4.getId());
        Section section1 = sectionRepository.save(항상_보이는_섹션(sectionQuestion1));
        sectionRepository.save(항상_보이는_섹션(sectionQuestion2));
        List<Long> sectionIds = List.of(section1.getId());
        Template template = templateRepository.save(템플릿(sectionIds));

        // when
        Set<Long> actual = questionRepository.findAllQuestionIdByTemplateId(template.getId());

        // then
        assertThat(actual).containsExactlyInAnyOrder(question1.getId(), question2.getId());
    }
}
