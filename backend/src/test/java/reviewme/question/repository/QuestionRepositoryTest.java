package reviewme.question.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
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

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

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

    @Test
    void 질문_아이디에_해당하는_모든_옵션_아이템을_불러온다() {
        // given
        Question question1 = questionRepository.save(선택형_필수_질문());
        Question question2 = questionRepository.save(선택형_필수_질문());
        OptionGroup optionGroup1 = optionGroupRepository.save(선택지_그룹(question1.getId()));
        OptionGroup optionGroup2 = optionGroupRepository.save(선택지_그룹(question2.getId()));

        OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup1.getId()));
        OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup1.getId()));
        OptionItem optionItem3 = optionItemRepository.save(선택지(optionGroup2.getId()));

        // when
        List<OptionItem> optionItemsForQuestion1 = questionRepository.findAllOptionItemsById(question1.getId());

        // then
        assertThat(optionItemsForQuestion1).containsOnly(optionItem1, optionItem2);
    }
}
