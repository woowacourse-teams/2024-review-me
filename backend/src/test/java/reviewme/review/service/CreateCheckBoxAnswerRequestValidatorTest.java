package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static reviewme.fixture.OptionGroupFixture.카테고리_선택지_그룹;
import static reviewme.fixture.OptionItemFixture.카테고리_선택지;
import static reviewme.fixture.QuestionFixture.카테고리_질문_선택형;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.question.domain.OptionGroup;
import reviewme.question.domain.OptionItem;
import reviewme.question.domain.Question;
import reviewme.question.domain.exception.QuestionNotFoundException;
import reviewme.question.repository.OptionGroupRepository;
import reviewme.question.repository.OptionItemRepository;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.service.dto.request.CreateReviewAnswerRequest;
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
    private QuestionRepository questionRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private OptionItemRepository optionItemRepository;

    @Test
    void 저장되지_않은_질문에_대한_응답이면_예외가_발생한다() {
        // given
        long notSavedQuestionId = 1L;

        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(
                notSavedQuestionId, List.of(), null
        );

        // when, then
        assertThatCode(() -> createCheckBoxAnswerRequestValidator.validate(request))
                .isInstanceOf(QuestionNotFoundException.class);
    }

    @Test
    void 선택형_질문에_텍스트_응답을_하면_예외가_발생한다() {
        // given
        String textAnswerInCheckboxQuestion = "서술형 응답";
        Question question = questionRepository.save(카테고리_질문_선택형.create());

        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(
                question.getId(), List.of(), textAnswerInCheckboxQuestion
        );

        // when, then
        assertThatCode(() -> createCheckBoxAnswerRequestValidator.validate(request))
                .isInstanceOf(CheckBoxAnswerIncludedTextException.class);
    }

    @Test
    void 저장되지_않은_옵션그룹에_대해_응답하면_예외가_발생한다() {
        // given
        long notSavedOptionGroupOptionItemId = 1L;
        Question question = questionRepository.save(카테고리_질문_선택형.create());

        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(
                question.getId(), List.of(notSavedOptionGroupOptionItemId), null
        );

        // when, then
        assertThatCode(() -> createCheckBoxAnswerRequestValidator.validate(request))
                .isInstanceOf(OptionGroupNotFoundByQuestionIdException.class);
    }

    @Test
    void 필수_선택형_질문에_응답을_하지_않으면_예외가_발생한다() {
        // given
        Question question = questionRepository.save(카테고리_질문_선택형.create());
        optionGroupRepository.save(카테고리_선택지_그룹.createWithQuestionId(question.getId()));

        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(
                question.getId(), null, null
        );

        // when, then
        assertThatCode(() -> createCheckBoxAnswerRequestValidator.validate(request))
                .isInstanceOf(MissingRequiredQuestionAnswerException.class);
    }

    @Test
    void 옵션그룹에서_제공하지_않은_옵션아이템을_응답하면_예외가_발생한다() {
        // given
        long notSavedOptionItem = 1L;
        Question savedQuestion = questionRepository.save(카테고리_질문_선택형.create());
        optionGroupRepository.save(카테고리_선택지_그룹.createWithQuestionId(savedQuestion.getId()));

        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(
                savedQuestion.getId(), List.of(notSavedOptionItem), null
        );

        // when, then
        assertThatCode(() -> createCheckBoxAnswerRequestValidator.validate(request))
                .isInstanceOf(CheckBoxAnswerIncludedNotProvidedOptionItemException.class);
    }

    @Test
    void 옵션그룹에서_정한_최소_선택_수_보다_적게_선택하면_예외가_발생한다() {
        // given
        Question question = questionRepository.save(카테고리_질문_선택형.create());
        OptionGroup optionGroup = optionGroupRepository.save(new OptionGroup(question.getId(), 2, 2));
        OptionItem savedOptionItem1 = optionItemRepository.save(카테고리_선택지.createWithOptionGroupId(optionGroup.getId()));

        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(
                question.getId(), List.of(savedOptionItem1.getId()), null
        );

        // when, then
        assertThatCode(() -> createCheckBoxAnswerRequestValidator.validate(request))
                .isInstanceOf(SelectedCheckBoxAnswerCountOutOfRange.class);
    }

    @Test
    void 옵션그룹에서_정한_최대_선택_수_보다_많이_선택하면_예외가_발생한다() {
        // given
        Question question = questionRepository.save(카테고리_질문_선택형.create());
        OptionGroup optionGroup = optionGroupRepository.save(new OptionGroup(question.getId(), 1, 1));
        OptionItem optionItem1 = optionItemRepository.save(카테고리_선택지.createWithOptionGroupId(optionGroup.getId()));
        OptionItem optionItem2 = optionItemRepository.save(카테고리_선택지.createWithOptionGroupId(optionGroup.getId()));

        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(
                question.getId(), List.of(optionItem1.getId(), optionItem2.getId()), null
        );

        // when, then
        assertThatCode(() -> createCheckBoxAnswerRequestValidator.validate(request))
                .isInstanceOf(SelectedCheckBoxAnswerCountOutOfRange.class);
    }
}
