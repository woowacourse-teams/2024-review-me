package reviewme.review.service.validator;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지;
import static reviewme.fixture.QuestionFixture.서술형_옵션_질문;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.조건부로_보이는_섹션;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    private NewReviewValidator reviewValidator;

    @Test
    void 템플릿에_있는_질문에_대한_답과_필수_질문에_모두_응답하는_경우_예외가_발생하지_않는다() {
        // 리뷰 그룹 저장
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // 필수가 아닌 서술형 질문 저장
        Question notRequiredTextQuestion = questionRepository.save(서술형_옵션_질문());
        Section visibleSection1 = sectionRepository.save(항상_보이는_섹션(List.of(notRequiredTextQuestion.getId()), 1));

        // 필수 선택형 질문, 섹션 저장
        Question requiredCheckQuestion = questionRepository.save(선택형_필수_질문());
        OptionGroup requiredOptionGroup = optionGroupRepository.save(선택지_그룹(requiredCheckQuestion.getId()));
        OptionItem requiredOptionItem1 = optionItemRepository.save(선택지(requiredOptionGroup.getId()));
        OptionItem requiredOptionItem2 = optionItemRepository.save(선택지(requiredOptionGroup.getId()));
        Section visibleSection2 = sectionRepository.save(항상_보이는_섹션(List.of(requiredCheckQuestion.getId()), 2));

        // optionItem 선택에 따라서 required 가 달라지는 섹션1 저장
        Question conditionalTextQuestion1 = questionRepository.save(서술형_필수_질문());
        Question conditionalCheckQuestion = questionRepository.save(선택형_필수_질문());
        OptionGroup conditionalOptionGroup = optionGroupRepository.save(선택지_그룹(conditionalCheckQuestion.getId()));
        OptionItem conditionalOptionItem = optionItemRepository.save(선택지(conditionalOptionGroup.getId()));
        Section conditionalSection1 = sectionRepository.save(조건부로_보이는_섹션(
                List.of(conditionalTextQuestion1.getId(), conditionalCheckQuestion.getId()),
                requiredOptionItem1.getId(), 3)
        );

        // optionItem 선택에 따라서 required 가 달라지는 섹션2 저장
        Question conditionalQuestion2 = questionRepository.save(서술형_필수_질문());
        Section conditionalSection2 = sectionRepository.save(조건부로_보이는_섹션(
                List.of(conditionalQuestion2.getId()), requiredOptionItem2.getId(), 3)
        );

        // 템플릿 저장
        Template template = templateRepository.save(템플릿(
                List.of(visibleSection1.getId(), visibleSection2.getId(),
                        conditionalSection1.getId(), conditionalSection2.getId())
        ));

        // 각 질문에 대한 답변 생성
        TextAnswer notRequiredTextAnswer = new TextAnswer(notRequiredTextQuestion.getId(), "답변".repeat(30));
        CheckboxAnswer alwaysRequiredCheckAnswer = new CheckboxAnswer(requiredCheckQuestion.getId(),
                List.of(requiredOptionItem1.getId()));
        TextAnswer conditionalTextAnswer1 = new TextAnswer(conditionalTextQuestion1.getId(), "답변".repeat(30));
        CheckboxAnswer conditionalCheckAnswer1 = new CheckboxAnswer(conditionalCheckQuestion.getId(),
                List.of(conditionalOptionItem.getId()));

        // 리뷰 생성
        Review review = new Review(template.getId(), reviewGroup.getId(),
                List.of(notRequiredTextAnswer, conditionalTextAnswer1,
                        alwaysRequiredCheckAnswer, conditionalCheckAnswer1));

        // when, then
        assertThatCode(() -> reviewValidator.validate(review))
                .doesNotThrowAnyException();
    }

    @Test
    void 제공된_템플릿에_없는_질문에_대한_답변이_있을_경우_예외가_발생한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        Question question1 = questionRepository.save(서술형_필수_질문());
        Question question2 = questionRepository.save(서술형_필수_질문());
        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question1.getId())));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        TextAnswer textAnswer = new TextAnswer(question2.getId(), "답변".repeat(20));
        Review review = new Review(template.getId(), reviewGroup.getId(), List.of(textAnswer));

        // when, then
        assertThatThrownBy(() -> reviewValidator.validate(review))
                .isInstanceOf(SubmittedQuestionAndProvidedQuestionMismatchException.class);
    }

    @Test
    void 필수_질문에_답변하지_않은_경우_예외가_발생한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        Question requiredQuestion = questionRepository.save(서술형_필수_질문());
        Question optionalQuestion = questionRepository.save(서술형_옵션_질문());
        Section section = sectionRepository.save(
                항상_보이는_섹션(List.of(requiredQuestion.getId(), optionalQuestion.getId())));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        TextAnswer optionalTextAnswer = new TextAnswer(optionalQuestion.getId(), "답변".repeat(20));
        Review review = new Review(template.getId(), reviewGroup.getId(), List.of(optionalTextAnswer));

        // when, then
        assertThatThrownBy(() -> reviewValidator.validate(review))
                .isInstanceOf(MissingRequiredQuestionException.class);
    }
}
