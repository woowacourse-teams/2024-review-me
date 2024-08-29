package reviewme.review.service.module;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.fixture.OptionGroupFixture;
import reviewme.fixture.QuestionFixture;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
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

    private QuestionFixture questionFixture = new QuestionFixture();
    private OptionGroupFixture optionGroupFixture = new OptionGroupFixture();
    private Question savedQuestion;

    @BeforeEach
    void setUp() {
        savedQuestion = questionRepository.save(questionFixture.선택형_필수_질문());
    }

    @Test
    void 저장되지_않은_질문에_대한_응답이면_예외가_발생한다() {
        // given
        long notSavedQuestionId = 100L;
        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(notSavedQuestionId, List.of(1L));

        // when, then
        assertThatCode(() -> checkBoxAnswerValidator.validate(checkboxAnswer))
                .isInstanceOf(SubmittedQuestionNotFoundException.class);
    }

    @Test
    void 저장되지_않은_옵션그룹에_대해_응답하면_예외가_발생한다() {
        // given
        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(savedQuestion.getId(), List.of(1L));

        // when, then
        assertThatCode(() -> checkBoxAnswerValidator.validate(checkboxAnswer))
                .isInstanceOf(OptionGroupNotFoundByQuestionIdException.class);
    }

//    @Test
//    void 필수_선택형_질문에_응답을_하지_않으면_예외가_발생한다() {
//        // given
//        optionGroupRepository.save(optionGroupFixture.선택지_그룹(savedQuestion.getId()));
//        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(savedQuestion.getId(), null);
//
//        // when, then
//        assertThatCode(() -> checkBoxAnswerValidator.validate(checkboxAnswer))
//                .isInstanceOf(QuestionNotAnsweredException.class);
//    }

    @Test
    void 옵션그룹에서_제공하지_않은_옵션아이템을_응답하면_예외가_발생한다() {
        // given
        OptionGroup savedOptionGroup = optionGroupRepository.save(
                new OptionGroup(savedQuestion.getId(), 1, 3)
        );
        OptionItem savedOptionItem = optionItemRepository.save(
                new OptionItem("옵션", savedOptionGroup.getId(), 1, OptionType.KEYWORD)
        );

        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(savedQuestion.getId(),
                List.of(savedOptionItem.getId() + 1L));

        // when, then
        assertThatCode(() -> checkBoxAnswerValidator.validate(checkboxAnswer))
                .isInstanceOf(CheckBoxAnswerIncludedNotProvidedOptionItemException.class);
    }

    @Test
    void 옵션그룹에서_정한_최소_선택_수_보다_적게_선택하면_예외가_발생한다() {
        // given
        OptionGroup savedOptionGroup = optionGroupRepository.save(
                new OptionGroup(savedQuestion.getId(), 2, 3)
        );
        OptionItem savedOptionItem1 = optionItemRepository.save(
                new OptionItem("옵션1", savedOptionGroup.getId(), 1, OptionType.KEYWORD)
        );

        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(savedQuestion.getId(), List.of(savedOptionItem1.getId()));

        // when, then
        assertThatCode(() -> checkBoxAnswerValidator.validate(checkboxAnswer))
                .isInstanceOf(SelectedOptionItemCountOutOfRangeException.class);
    }

    @Test
    void 옵션그룹에서_정한_최대_선택_수_보다_많이_선택하면_예외가_발생한다() {
        // given
        OptionGroup savedOptionGroup = optionGroupRepository.save(
                new OptionGroup(savedQuestion.getId(), 1, 1)
        );
        OptionItem savedOptionItem1 = optionItemRepository.save(
                new OptionItem("옵션1", savedOptionGroup.getId(), 1, OptionType.KEYWORD)
        );
        OptionItem savedOptionItem2 = optionItemRepository.save(
                new OptionItem("옵션2", savedOptionGroup.getId(), 2, OptionType.KEYWORD)
        );

        CheckboxAnswer checkboxAnswer = new CheckboxAnswer(
                savedQuestion.getId(), List.of(savedOptionItem1.getId(), savedOptionItem2.getId()));

        // when, then
        assertThatCode(() -> checkBoxAnswerValidator.validate(checkboxAnswer))
                .isInstanceOf(SelectedOptionItemCountOutOfRangeException.class);
    }
}
