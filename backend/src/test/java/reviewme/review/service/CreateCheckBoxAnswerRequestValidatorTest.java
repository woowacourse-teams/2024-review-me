package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.OptionType;
import reviewme.question.domain.Question2;
import reviewme.question.domain.QuestionType;
import reviewme.question.domain.exception.QuestionNotFoundException;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.review.dto.request.CreateReviewAnswerRequest;
import reviewme.review.repository.QuestionRepository2;
import reviewme.review.service.exception.CheckBoxAnswerIncludedNotProvidedOptionItemException;
import reviewme.review.service.exception.CheckBoxAnswerIncludedTextException;
import reviewme.review.service.exception.MissingRequiredQuestionAnswerException;
import reviewme.review.service.exception.SelectedCheckBoxAnswerCountOutOfRange;
import reviewme.support.ServiceTest;
import reviewme.template.domain.exception.OptionGroupNotFoundByQuestionIdException;

@ServiceTest
class CreateCheckBoxAnswerRequestValidatorTest {

    @Autowired
    private CreateCheckBoxAnswerRequestValidator createCheckBoxAnswerRequestValidator;

    @Autowired
    private QuestionRepository2 questionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    private Question2 savedQuestion;

    @BeforeEach
    void setUp() {
        savedQuestion = questionRepository.save(new Question2(true, QuestionType.CHECKBOX, "질문", null, 1));
    }

    @Test
    void 저장되지_않은_질문에_대한_응답이면_예외가_발생한다() {
        // given
        long notSavedQuestionId = 100L;
        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(
                notSavedQuestionId, List.of(1L), null
        );

        // when, then
        assertThatCode(() -> createCheckBoxAnswerRequestValidator.validate(request))
                .isInstanceOf(QuestionNotFoundException.class);
    }

    @Test
    void 선택형_질문에_텍스트_응답을_하면_예외가_발생한다() {
        // given
        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(
                savedQuestion.getId(), List.of(1L), "서술형 응답"
        );

        // when, then
        assertThatCode(() -> createCheckBoxAnswerRequestValidator.validate(request))
                .isInstanceOf(CheckBoxAnswerIncludedTextException.class);
    }

    @Test
    void 저장되지_않은_옵션그룹에_대해_응답하면_예외가_발생한다() {
        // given
        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(
                savedQuestion.getId(), List.of(1L), null
        );

        // when, then
        assertThatCode(() -> createCheckBoxAnswerRequestValidator.validate(request))
                .isInstanceOf(OptionGroupNotFoundByQuestionIdException.class);
    }

    @Test
    void 필수_선택형_질문에_응답을_하지_않으면_예외가_발생한다() {
        // given
        optionGroupRepository.save(
                new OptionGroup(savedQuestion.getId(), 1, 3)
        );
        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(
                savedQuestion.getId(),
                null,
                null);

        // when, then
        assertThatCode(() -> createCheckBoxAnswerRequestValidator.validate(request))
                .isInstanceOf(MissingRequiredQuestionAnswerException.class);
    }

    @Test
    void 옵션그룹에서_제공하지_않은_옵션아이템을_응답하면_예외가_발생한다() {
        // given
        OptionGroup savedOptionGroup = optionGroupRepository.save(
                new OptionGroup(savedQuestion.getId(), 1, 3)
        );
        OptionItem savedOptionItem = optionItemRepository.save(
                new OptionItem("옵션", savedOptionGroup.getId(), 1, OptionType.KEYWORD)
        );

        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(
                savedQuestion.getId(), List.of(savedOptionItem.getId() + 1L), null
        );

        // when, then
        assertThatCode(() -> createCheckBoxAnswerRequestValidator.validate(request))
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

        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(
                savedQuestion.getId(), List.of(savedOptionItem1.getId()), null
        );

        // when, then
        assertThatCode(() -> createCheckBoxAnswerRequestValidator.validate(request))
                .isInstanceOf(SelectedCheckBoxAnswerCountOutOfRange.class);
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

        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(
                savedQuestion.getId(), List.of(savedOptionItem1.getId(), savedOptionItem2.getId()), null
        );

        // when, then
        assertThatCode(() -> createCheckBoxAnswerRequestValidator.validate(request))
                .isInstanceOf(SelectedCheckBoxAnswerCountOutOfRange.class);
    }
}
