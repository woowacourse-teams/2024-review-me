package reviewme.review.domain.policy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지;
import static reviewme.fixture.QuestionFixture.서술형_옵션_질문;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.exception.InvalidTextAnswerLengthException;
import reviewme.support.ServiceTest;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.Question;
import reviewme.template.domain.Section;
import reviewme.template.domain.StructuredTemplate;
import reviewme.template.domain.Template;
import reviewme.template.repository.OptionGroupRepository;
import reviewme.template.repository.OptionItemRepository;
import reviewme.template.repository.QuestionRepository;
import reviewme.template.repository.SectionRepository;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class TextAnswerRegistrationPolicyTest {

    @Autowired
    private TextAnswerRegistrationPolicy textAnswerRegistrationPolicy;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Test
    void 서술형_질문이_아닐경우_예외가_발생한다() {
        Question question = questionRepository.save(선택형_필수_질문());
        OptionGroup optionGroup = optionGroupRepository.save(선택지_그룹(question.getId()));
        OptionItem optionItem = optionItemRepository.save(선택지(optionGroup.getId()));
        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(question.getId(),
                List.of(optionItem.getId() + 1L));

        StructuredTemplate structuredTemplate = new StructuredTemplate(
                template, List.of(section), List.of(question), List.of(optionGroup), List.of(optionItem));
        TemplateValidationContext templateContext = new TemplateValidationContext(structuredTemplate);

        assertThatThrownBy(() -> textAnswerRegistrationPolicy.verify(checkboxAnswer, templateContext))
                .isInstanceOf(ClassCastException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {19, 10001})
    void 필수_질문의_답변_길이가_유효하지_않으면_예외가_발생한다(int length) {
        // given
        Question question = questionRepository.save(서술형_필수_질문());
        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        String content = "답".repeat(length);
        TextAnswer textAnswer = new TextAnswer(question.getId(), content);

        StructuredTemplate structuredTemplate = new StructuredTemplate(template, List.of(section), List.of(question));
        TemplateValidationContext templateContext = new TemplateValidationContext(structuredTemplate);

        // when, then
        assertThatThrownBy(() -> textAnswerRegistrationPolicy.verify(textAnswer, templateContext))
                .isInstanceOf(InvalidTextAnswerLengthException.class);
    }

    @Test
    void 선택_질문의_답변_길이가_유효하지_않으면_예외가_발생한다() {
        // given
        Question question = questionRepository.save(서술형_필수_질문());
        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        String content = "답".repeat(10001);
        TextAnswer textAnswer = new TextAnswer(question.getId(), content);

        StructuredTemplate structuredTemplate = new StructuredTemplate(template, List.of(section), List.of(question));
        TemplateValidationContext templateContext = new TemplateValidationContext(structuredTemplate);

        // when, then
        assertThatThrownBy(() -> textAnswerRegistrationPolicy.verify(textAnswer, templateContext))
                .isInstanceOf(InvalidTextAnswerLengthException.class);
    }

    @Test
    void 선택_질문은_최소_글자수_제한을_받지_않는다() {
        // given
        Question question = questionRepository.save(서술형_옵션_질문());
        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        String content = "답".repeat(1);
        TextAnswer textAnswer = new TextAnswer(question.getId(), content);

        StructuredTemplate structuredTemplate = new StructuredTemplate(template, List.of(section), List.of(question));
        TemplateValidationContext templateContext = new TemplateValidationContext(structuredTemplate);

        // when, then
        assertDoesNotThrow(() -> textAnswerRegistrationPolicy.verify(textAnswer, templateContext));
    }
}
