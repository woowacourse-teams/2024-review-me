package reviewme.review.domain.policy;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;
import static reviewme.fixture.TemplateFixture.템플릿;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.exception.CheckBoxAnswerIncludedNotProvidedOptionItemException;
import reviewme.review.domain.exception.SelectedOptionItemCountOutOfRangeException;
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
class CheckBoxAnswerRegistrationPolicyTest {

    @Autowired
    private CheckBoxAnswerRegistrationPolicy checkBoxAnswerRegistrationPolicy;

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
    void 선택형_질문이_아닐경우_예외가_발생한다() {
        Question question = questionRepository.save(서술형_필수_질문());
        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        String content = "답".repeat(20);
        TextAnswer textAnswer = new TextAnswer(question.getId(), content);

        StructuredTemplate structuredTemplate = new StructuredTemplate(template, List.of(section), List.of(question));
        TemplateValidationContext templateContext = new TemplateValidationContext(structuredTemplate);

        assertThatThrownBy(() -> checkBoxAnswerRegistrationPolicy.verify(textAnswer, templateContext))
                .isInstanceOf(ClassCastException.class);
    }

    @Test
    void 옵션그룹에서_제공하지_않은_옵션아이템을_응답하면_예외가_발생한다() {
        // given
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

        // when, then
        assertThatCode(() -> checkBoxAnswerRegistrationPolicy.verify(checkboxAnswer, templateContext))
                .isInstanceOf(CheckBoxAnswerIncludedNotProvidedOptionItemException.class);
    }

    @Test
    void 옵션그룹에서_정한_최소_선택_수_보다_적게_선택하면_예외가_발생한다() {
        // given
        Question question = questionRepository.save(선택형_필수_질문());
        OptionGroup optionGroup = optionGroupRepository.save(
                new OptionGroup(question.getId(), 2, 3)
        );
        OptionItem optionItem = optionItemRepository.save(선택지(optionGroup.getId()));
        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(question.getId(),
                List.of(optionItem.getId()));

        StructuredTemplate structuredTemplate = new StructuredTemplate(
                template, List.of(section), List.of(question), List.of(optionGroup), List.of(optionItem));
        TemplateValidationContext templateContext = new TemplateValidationContext(structuredTemplate);

        // when, then
        assertThatCode(() -> checkBoxAnswerRegistrationPolicy.verify(checkboxAnswer, templateContext))
                .isInstanceOf(SelectedOptionItemCountOutOfRangeException.class);
    }

    @Test
    void 옵션그룹에서_정한_최대_선택_수_보다_많이_선택하면_예외가_발생한다() {
        // given
        Question question = questionRepository.save(선택형_필수_질문());
        OptionGroup optionGroup = optionGroupRepository.save(
                new OptionGroup(question.getId(), 1, 1)
        );
        OptionItem optionItem1 = optionItemRepository.save(선택지(optionGroup.getId(), 1));
        OptionItem optionItem2 = optionItemRepository.save(선택지(optionGroup.getId(), 2));
        Section section = sectionRepository.save(항상_보이는_섹션(List.of(question.getId())));
        Template template = templateRepository.save(템플릿(List.of(section.getId())));

        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(
                question.getId(), List.of(optionItem1.getId(), optionItem2.getId()));

        StructuredTemplate structuredTemplate = new StructuredTemplate(
                template, List.of(section), List.of(question), List.of(optionGroup), List.of(optionItem1, optionItem2));
        TemplateValidationContext templateContext = new TemplateValidationContext(structuredTemplate);

        // when, then
        assertThatCode(() -> checkBoxAnswerRegistrationPolicy.verify(checkboxAnswer, templateContext))
                .isInstanceOf(SelectedOptionItemCountOutOfRangeException.class);
    }
}
