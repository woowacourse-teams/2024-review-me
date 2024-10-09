package reviewme.question.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.question.domain.Question;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
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

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Test
    void 템플릿_아이디로_질문_목록_아이디를_모두_가져온다() {
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
        List<Question> actual = questionRepository.findAllByTemplatedId(template.getId());

        // then
        assertThat(actual).containsExactlyInAnyOrder(question1, question2);
    }

    @Test
    void 리뷰_요청_코드와_섹션_아이디에_해당하는_질문을_모두_가져온다() {
        // given
        String reviewRequestCode = "12341234";
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹(reviewRequestCode, "43214321"));

        Question question1 = questionRepository.save(서술형_필수_질문(1));
        Question question2 = questionRepository.save(서술형_필수_질문(2));
        Question question3 = questionRepository.save(서술형_필수_질문(1));
        Question question4 = questionRepository.save(서술형_필수_질문(2));

        List<Long> sectionQuestion1 = List.of(question1.getId(), question2.getId());
        List<Long> sectionQuestion2 = List.of(question3.getId(), question4.getId());
        Section section1 = sectionRepository.save(항상_보이는_섹션(sectionQuestion1));
        Section section2 = sectionRepository.save(항상_보이는_섹션(sectionQuestion2));
        List<Long> sectionIds = List.of(section1.getId(), section2.getId());
        Template template = templateRepository.save(템플릿(sectionIds));

        // when
        List<Question> questionsInSection1 = questionRepository.findAllByReviewRequestCodeAndSectionId(
                reviewRequestCode, section1.getId());
        List<Question> questionsInSection2 = questionRepository.findAllByReviewRequestCodeAndSectionId(
                reviewRequestCode, section2.getId());


        // then
        assertAll(
                () -> assertThat(questionsInSection1).containsOnly(question1, question2),
                () -> assertThat(questionsInSection2).containsOnly(question3, question4)
        );
    }
}
