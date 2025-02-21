package reviewme.review.service.validator;

import static org.assertj.core.api.Assertions.assertThatCode;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;
import static reviewme.fixture.SectionFixture.항상_보이는_섹션;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.service.exception.CheckBoxAnswerIncludedNotProvidedOptionItemException;
import reviewme.review.service.exception.OptionGroupNotFoundByQuestionIdException;
import reviewme.review.service.exception.SelectedOptionItemCountOutOfRangeException;
import reviewme.review.service.exception.SubmittedQuestionNotFoundException;
import reviewme.support.ServiceTest;
import reviewme.template.domain.OptionGroup;
import reviewme.template.domain.OptionItem;
import reviewme.template.domain.Question;
import reviewme.template.domain.QuestionType;
import reviewme.template.domain.Section;
import reviewme.template.domain.Template;
import reviewme.template.repository.TemplateRepository;

@ServiceTest
class CheckboxTypedAnswerValidatorTest {

    @Autowired
    private CheckboxTypedAnswerValidator checkBoxAnswerValidator;

    @Autowired
    private TemplateRepository templateRepository;

    @Test
    void 저장되지_않은_질문에_대한_답변이면_예외가_발생한다() {
        // given
        long notSavedQuestionId = 100L;
        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(notSavedQuestionId, List.of(1L));

        // when, then
        assertThatCode(() -> checkBoxAnswerValidator.validate(checkboxAnswer))
                .isInstanceOf(SubmittedQuestionNotFoundException.class);
    }

    @Test
    void 옵션_그룹이_지정되지_않은_질문에_대한_답변이면_예외가_발생한다() {
        // given
        Question savedQuestion = 선택형_필수_질문();
        Section section = 항상_보이는_섹션(List.of(savedQuestion));
        templateRepository.save(new Template(List.of(section)));

        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(savedQuestion.getId(), List.of(1L));

        // when, then
        assertThatCode(() -> checkBoxAnswerValidator.validate(checkboxAnswer))
                .isInstanceOf(OptionGroupNotFoundByQuestionIdException.class);
    }

    @Test
    void 옵션그룹에서_제공하지_않은_옵션아이템을_응답하면_예외가_발생한다() {
        // given
        OptionItem savedOptionItem = 선택지();
        OptionGroup savedOptionGroup = 선택지_그룹(List.of(savedOptionItem));
        Question savedQuestion = new Question(true, QuestionType.CHECKBOX, savedOptionGroup, "질문", "설명", 1);
        Section section = 항상_보이는_섹션(List.of(savedQuestion));
        templateRepository.save(new Template(List.of(section)));

        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(savedQuestion.getId(),
                List.of(savedOptionItem.getId() + 1L));

        // when, then
        assertThatCode(() -> checkBoxAnswerValidator.validate(checkboxAnswer))
                .isInstanceOf(CheckBoxAnswerIncludedNotProvidedOptionItemException.class);
    }

    @Test
    void 옵션그룹에서_정한_최소_선택_수_보다_적게_선택하면_예외가_발생한다() {
        // given
        OptionItem savedOptionItem1 = 선택지();
        OptionItem savedOptionItem2 = 선택지();
        OptionItem savedOptionItem3 = 선택지();
        OptionGroup savedOptionGroup = new OptionGroup(
                List.of(savedOptionItem1, savedOptionItem2, savedOptionItem3), 2, 3
        );
        Question savedQuestion = new Question(true, QuestionType.CHECKBOX, savedOptionGroup, "질문", "설명", 1);
        Section section = 항상_보이는_섹션(List.of(savedQuestion));
        templateRepository.save(new Template(List.of(section)));

        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(savedQuestion.getId(), List.of(savedOptionItem1.getId()));

        // when, then
        assertThatCode(() -> checkBoxAnswerValidator.validate(checkboxAnswer))
                .isInstanceOf(SelectedOptionItemCountOutOfRangeException.class);
    }

    @Test
    void 옵션그룹에서_정한_최대_선택_수_보다_많이_선택하면_예외가_발생한다() {
        // given
        OptionItem savedOptionItem1 = 선택지();
        OptionItem savedOptionItem2 = 선택지();
        OptionItem savedOptionItem3 = 선택지();
        OptionGroup savedOptionGroup = new OptionGroup(
                List.of(savedOptionItem1, savedOptionItem2, savedOptionItem3), 1, 1
        );
        Question savedQuestion = new Question(true, QuestionType.CHECKBOX, savedOptionGroup, "질문", "설명", 1);
        Section section = 항상_보이는_섹션(List.of(savedQuestion));
        templateRepository.save(new Template(List.of(section)));

        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(
                savedQuestion.getId(), List.of(savedOptionItem1.getId(), savedOptionItem2.getId())
        );

        // when, then
        assertThatCode(() -> checkBoxAnswerValidator.validate(checkboxAnswer))
                .isInstanceOf(SelectedOptionItemCountOutOfRangeException.class);
    }
}
