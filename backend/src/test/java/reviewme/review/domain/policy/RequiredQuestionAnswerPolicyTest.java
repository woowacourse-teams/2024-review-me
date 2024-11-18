package reviewme.review.domain.policy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static reviewme.fixture.QuestionFixture.서술형_옵션_질문;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.exception.MissingRequiredQuestionException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.StructuredTemplate;
import reviewme.template.domain.Template;
import reviewme.template.repository.QuestionRepository;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class RequiredQuestionAnswerPolicyTest {

    @Autowired
    private RequiredQuestionAnswerPolicy requiredQuestionAnswerPolicy;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private SectionRepository sectionRepository;

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

        StructuredTemplate structuredTemplate = new StructuredTemplate(
                template, List.of(section), List.of(requiredQuestion, optionalQuestion));
        TemplateValidationContext templateContext = new TemplateValidationContext(structuredTemplate);

        // when, then
        assertThatThrownBy(() -> requiredQuestionAnswerPolicy.verify(review, templateContext))
                .isInstanceOf(MissingRequiredQuestionException.class);
    }
}
