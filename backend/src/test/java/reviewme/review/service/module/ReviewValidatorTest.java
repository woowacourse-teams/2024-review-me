package reviewme.review.service.module;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.fixture.OptionGroupFixture;
import reviewme.fixture.OptionItemFixture;
import reviewme.fixture.QuestionFixture;
import reviewme.fixture.ReviewGroupFixture;
import reviewme.fixture.SectionFixture;
import reviewme.fixture.TemplateFixture;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.exception.MissingRequiredQuestionException;
import reviewme.review.service.exception.SubmittedQuestionAndProvidedQuestionMismatchException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class ReviewValidatorTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ReviewValidator reviewValidator;

    private ReviewGroupFixture reviewGroupFixture = new ReviewGroupFixture();
    private TemplateFixture templateFixture = new TemplateFixture();
    private SectionFixture sectionFixture = new SectionFixture();
    private QuestionFixture questionFixture = new QuestionFixture();
    private OptionGroupFixture optionGroupFixture = new OptionGroupFixture();
    private OptionItemFixture optionItemFixture = new OptionItemFixture();

    @Test
    void 템플릿에_있는_질문에_대한_답과_필수_질문에_모두_응답하는_경우_예외가_발생하지_않는다() {
        // 리뷰 그룹 저장
        ReviewGroup reviewGroup = reviewGroupRepository.save(reviewGroupFixture.리뷰_그룹());

        // 필수가 아닌 서술형 질문 저장
        Question notRequiredTextQuestion = questionRepository.save(questionFixture.서술형_옵션_질문());
        Section visibleSection1 = sectionRepository.save(sectionFixture.항상_보이는_섹션(List.of(notRequiredTextQuestion.getId()), 1));

        // 필수 선택형 질문, 섹션 저장
        Question requiredCheckQuestion = questionRepository.save(questionFixture.선택형_필수_질문());
        OptionGroup requiredOptionGroup = optionGroupRepository.save(optionGroupFixture.선택지_그룹(requiredCheckQuestion.getId()));
        OptionItem requiredOptionItem1 = optionItemRepository.save(optionItemFixture.선택지(requiredOptionGroup.getId()));
        OptionItem requiredOptionItem2 = optionItemRepository.save(optionItemFixture.선택지(requiredOptionGroup.getId()));
        Section visibleSection2 = sectionRepository.save(sectionFixture.항상_보이는_섹션(List.of(requiredCheckQuestion.getId()), 2));

        // optionItem 선택에 따라서 required 가 달라지는 섹션1 저장
        Question conditionalTextQuestion1 = questionRepository.save(questionFixture.서술형_필수_질문());
        Question conditionalCheckQuestion = questionRepository.save(questionFixture.선택형_필수_질문());
        OptionGroup conditionalOptionGroup = optionGroupRepository.save(optionGroupFixture.선택지_그룹(conditionalCheckQuestion.getId()));
        OptionItem conditionalOptionItem = optionItemRepository.save(optionItemFixture.선택지(conditionalOptionGroup.getId()));
        Section conditionalSection1 = sectionRepository.save(sectionFixture.조건부로_보이는_섹션(
                List.of(conditionalTextQuestion1.getId(), conditionalCheckQuestion.getId()), requiredOptionItem1.getId(), 3)
        );

        // optionItem 선택에 따라서 required 가 달라지는 섹션2 저장
        Question conditionalQuestion2 = questionRepository.save(questionFixture.서술형_필수_질문());
        Section conditionalSection2 = sectionRepository.save(sectionFixture.조건부로_보이는_섹션(
                List.of(conditionalQuestion2.getId()), requiredOptionItem2.getId(), 3)
        );

        // 템플릿 저장
        Template template = templateRepository.save(templateFixture.템플릿(
                List.of(visibleSection1.getId(), visibleSection2.getId(),
                        conditionalSection1.getId(), conditionalSection2.getId())
        ));

        // 각 질문에 대한 답변 생성
        TextAnswer notRequiredlTextAnswer = new TextAnswer(notRequiredTextQuestion.getId(), "답변".repeat(30));
        CheckboxAnswer alwaysRequiredCheckAnswer = new CheckboxAnswer(requiredCheckQuestion.getId(), List.of(requiredOptionItem1.getId()));
        TextAnswer conditionalTextAnswer1 = new TextAnswer(conditionalTextQuestion1.getId(), "답변".repeat(30));
        CheckboxAnswer conditionalCheckAnswer1 = new CheckboxAnswer(conditionalCheckQuestion.getId(), List.of(conditionalOptionItem.getId()));

        // 리뷰 생성
        Review review = new Review(template.getId(), reviewGroup.getId(),
                List.of(notRequiredlTextAnswer, conditionalTextAnswer1), List.of(alwaysRequiredCheckAnswer, conditionalCheckAnswer1));

        // when, then
        assertThatCode(() -> reviewValidator.validate(review))
                .doesNotThrowAnyException();
    }

    @Test
    void 제공된_템플릿에_없는_질문에_대한_답변이_있을_경우_예외가_발생한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(reviewGroupFixture.리뷰_그룹());

        Question question1 = questionRepository.save(questionFixture.서술형_필수_질문());
        Question question2 = questionRepository.save(questionFixture.서술형_필수_질문());
        Section section = sectionRepository.save(sectionFixture.항상_보이는_섹션(List.of(question1.getId())));
        Template template = templateRepository.save(templateFixture.템플릿(List.of(section.getId())));

        TextAnswer textAnswer = new TextAnswer(question2.getId(), "서술형답변");
        Review review = new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer), new ArrayList<>());

        // when, then
        assertThatThrownBy(() -> reviewValidator.validate(review))
                .isInstanceOf(SubmittedQuestionAndProvidedQuestionMismatchException.class);
    }

    @Test
    void 필수_질문에_답변하지_않은_경우_예외가_발생한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(reviewGroupFixture.리뷰_그룹());

        Question requiredQuestion = questionRepository.save(questionFixture.서술형_필수_질문());
        Question optionalQuestion = questionRepository.save(questionFixture.서술형_옵션_질문());
        Section section = sectionRepository.save(sectionFixture.항상_보이는_섹션(List.of(requiredQuestion.getId(), optionalQuestion.getId())));
        Template template = templateRepository.save(templateFixture.템플릿(List.of(section.getId())));

        TextAnswer optionalTextAnswer = new TextAnswer(optionalQuestion.getId(), "서술형답변");
        Review review = new Review(template.getId(), reviewGroup.getId(), List.of(optionalTextAnswer), List.of());

        // when, then
        assertThatThrownBy(() -> reviewValidator.validate(review))
                .isInstanceOf(MissingRequiredQuestionException.class);
    }
}
