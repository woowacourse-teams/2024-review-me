package reviewme.review.service.module;

import static org.assertj.core.api.Assertions.assertThatCode;
import static reviewme.fixture.OptionGroupFixture.선택지_그룹;
import static reviewme.fixture.OptionItemFixture.선택지_카테고리;
import static reviewme.fixture.QuestionFixture.선택형_필수_질문;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.fixture.OptionItemFixture;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.service.exception.CheckBoxAnswerIncludedNotProvidedOptionItemException;
import reviewme.review.service.exception.SelectedOptionItemCountOutOfRangeException;
import reviewme.review.service.exception.SubmittedQuestionNotFoundException;
import reviewme.support.ServiceTest;
import reviewme.template.domain.exception.OptionGroupNotFoundByQuestionIdException;

@ServiceTest
class CheckBoxAnswerValidatorTest {

    @Autowired
    private CheckBoxAnswerValidator checkBoxAnswerValidator;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

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
        Question savedQuestion = questionRepository.save(선택형_필수_질문());
        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(savedQuestion.getId(), List.of(1L));

        // when, then
        assertThatCode(() -> checkBoxAnswerValidator.validate(checkboxAnswer))
                .isInstanceOf(OptionGroupNotFoundByQuestionIdException.class);
    }

    @Test
    void 옵션그룹에서_제공하지_않은_옵션아이템을_응답하면_예외가_발생한다() {
        // given
        Question savedQuestion = questionRepository.save(선택형_필수_질문());
        OptionGroup savedOptionGroup = optionGroupRepository.save(선택지_그룹(savedQuestion.getId()));
        OptionItem savedOptionItem = optionItemRepository.save(OptionItemFixture.선택지_카테고리(savedOptionGroup.getId()));

        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(savedQuestion.getId(),
                List.of(savedOptionItem.getId() + 1L));

        // when, then
        assertThatCode(() -> checkBoxAnswerValidator.validate(checkboxAnswer))
                .isInstanceOf(CheckBoxAnswerIncludedNotProvidedOptionItemException.class);
    }

    @Test
    void 옵션그룹에서_정한_최소_선택_수_보다_적게_선택하면_예외가_발생한다() {
        // given
        Question savedQuestion = questionRepository.save(선택형_필수_질문());
        OptionGroup savedOptionGroup = optionGroupRepository.save(
                new OptionGroup(savedQuestion.getId(), 2, 3)
        );
        OptionItem savedOptionItem1 = optionItemRepository.save(OptionItemFixture.선택지_카테고리(savedOptionGroup.getId()));

        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(savedQuestion.getId(), List.of(savedOptionItem1.getId()));

        // when, then
        assertThatCode(() -> checkBoxAnswerValidator.validate(checkboxAnswer))
                .isInstanceOf(SelectedOptionItemCountOutOfRangeException.class);
    }

    @Test
    void 옵션그룹에서_정한_최대_선택_수_보다_많이_선택하면_예외가_발생한다() {
        // given
        Question savedQuestion = questionRepository.save(선택형_필수_질문());
        OptionGroup savedOptionGroup = optionGroupRepository.save(
                new OptionGroup(savedQuestion.getId(), 1, 1)
        );
        OptionItem savedOptionItem1 = optionItemRepository.save(선택지_카테고리(savedOptionGroup.getId(), 1));
        OptionItem savedOptionItem2 = optionItemRepository.save(선택지_카테고리(savedOptionGroup.getId(), 2));

        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(
                savedQuestion.getId(), List.of(savedOptionItem1.getId(), savedOptionItem2.getId()));

        // when, then
        assertThatCode(() -> checkBoxAnswerValidator.validate(checkboxAnswer))
                .isInstanceOf(SelectedOptionItemCountOutOfRangeException.class);
    }
}
