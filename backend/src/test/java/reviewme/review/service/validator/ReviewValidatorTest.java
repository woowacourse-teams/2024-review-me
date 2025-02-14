package reviewme.review.service.validator;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지;
import static reviewme.fixture.QuestionFixture.서술형_옵션_질문;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.ReviewFixture.비회원_작성_리뷰;
import static reviewme.fixture.ReviewGroupFixture.비회원_리뷰_그룹;
import static reviewme.fixture.SectionFixture.조건부로_보이는_섹션;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.exception.MissingRequiredQuestionException;
import reviewme.review.service.exception.SubmittedQuestionAndProvidedQuestionMismatchException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.Question;
import reviewme.template.domain.QuestionType;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class ReviewValidatorTest {

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ReviewValidator reviewValidator;

    @Test
    void 템플릿에_있는_질문에_대한_답과_필수_질문에_모두_응답하는_경우_예외가_발생하지_않는다() {
        // 리뷰 그룹 저장
        ReviewGroup reviewGroup = reviewGroupRepository.save(비회원_리뷰_그룹());

        // 필수가 아닌 서술형 질문 저장
        Question notRequiredTextQuestion = 서술형_옵션_질문();
        Section visibleSection1 = 항상_보이는_섹션(List.of(notRequiredTextQuestion), 1);

        // 필수 선택형 질문, 섹션 저장
        OptionItem requiredOptionItem1 = 선택지();
        OptionItem requiredOptionItem2 = 선택지();
        OptionGroup requiredOptionGroup = 선택지_그룹(List.of(requiredOptionItem1, requiredOptionItem2));
        Question requiredCheckQuestion = new Question(true, QuestionType.CHECKBOX, requiredOptionGroup, "질문", "설명", 1);
        Section visibleSection2 = 항상_보이는_섹션(List.of(requiredCheckQuestion), 2);

        // optionItem 선택에 따라서 required 가 달라지는 섹션1 저장
        OptionItem conditionalOptionItem = 선택지();
        OptionGroup conditionalOptionGroup = 선택지_그룹(List.of(conditionalOptionItem));
        Question conditionalTextQuestion = 서술형_필수_질문();
        Question conditionalCheckQuestion = new Question(true, QuestionType.CHECKBOX, conditionalOptionGroup, "질문",
                "설명", 1);
        Section conditionalSection1 = 조건부로_보이는_섹션(
                List.of(conditionalTextQuestion, conditionalCheckQuestion), requiredOptionItem1, 3
        );

        // optionItem 선택에 따라서 required 가 달라지는 섹션2 저장
        Question conditionalQuestion2 = 서술형_필수_질문();
        Section conditionalSection2 = 조건부로_보이는_섹션(List.of(conditionalQuestion2), requiredOptionItem2, 3);

        // 템플릿 저장
        Template template = templateRepository.save(
                new Template(List.of(visibleSection1, visibleSection2, conditionalSection1, conditionalSection2))
        );

        // 각 질문에 대한 답변 생성
        TextAnswer notRequiredTextAnswer = new TextAnswer(notRequiredTextQuestion.getId(), "답변".repeat(30));
        CheckboxAnswer alwaysRequiredCheckAnswer = new CheckboxAnswer(requiredCheckQuestion.getId(),
                List.of(requiredOptionItem1.getId()));
        TextAnswer conditionalTextAnswer1 = new TextAnswer(conditionalTextQuestion.getId(), "답변".repeat(30));
        CheckboxAnswer conditionalCheckAnswer1 = new CheckboxAnswer(conditionalCheckQuestion.getId(),
                List.of(conditionalOptionItem.getId()));

        // 리뷰 생성
        Review review = 비회원_작성_리뷰(template.getId(), reviewGroup.getId(),
                List.of(notRequiredTextAnswer, conditionalTextAnswer1,
                        alwaysRequiredCheckAnswer, conditionalCheckAnswer1));

        // when, then
        assertThatCode(() -> reviewValidator.validate(review))
                .doesNotThrowAnyException();
    }

    @Test
    void 제공된_템플릿에_없는_질문에_대한_답변이_있을_경우_예외가_발생한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(비회원_리뷰_그룹());

        // 재공된 템플릿
        Question question1 = 서술형_필수_질문();
        Section section = 항상_보이는_섹션(List.of(question1));
        Template template = templateRepository.save(new Template(List.of(section)));

        // 다른 템플릿
        Question question2 = 서술형_필수_질문();
        Section section2 = 항상_보이는_섹션(List.of(question2));
        templateRepository.save(new Template(List.of(section2)));

        TextAnswer textAnswer = new TextAnswer(question2.getId(), "답변".repeat(20));
        Review review = 비회원_작성_리뷰(template.getId(), reviewGroup.getId(), List.of(textAnswer));

        // when, then
        assertThatThrownBy(() -> reviewValidator.validate(review))
                .isInstanceOf(SubmittedQuestionAndProvidedQuestionMismatchException.class);
    }

    @Test
    void 필수_질문에_답변하지_않은_경우_예외가_발생한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(비회원_리뷰_그룹());

        Question requiredQuestion = 서술형_필수_질문();
        Question optionalQuestion = 서술형_옵션_질문();
        Section section = 항상_보이는_섹션(List.of(requiredQuestion, optionalQuestion));
        Template template = templateRepository.save(new Template(List.of(section)));

        TextAnswer optionalTextAnswer = new TextAnswer(optionalQuestion.getId(), "답변".repeat(20));
        Review review = 비회원_작성_리뷰(template.getId(), reviewGroup.getId(), List.of(optionalTextAnswer));

        // when, then
        assertThatThrownBy(() -> reviewValidator.validate(review))
                .isInstanceOf(MissingRequiredQuestionException.class);
    }
}
