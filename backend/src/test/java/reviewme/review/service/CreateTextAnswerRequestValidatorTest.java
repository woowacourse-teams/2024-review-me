package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.question.domain.Question;
import reviewme.question.domain.QuestionType;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.service.dto.request.CreateReviewAnswerRequest;
import reviewme.review.service.exception.MissingRequiredAnswerException;
import reviewme.review.service.exception.SubmittedQuestionNotFoundException;
import reviewme.review.service.exception.TextAnswerIncludedOptionItemException;
import reviewme.support.ServiceTest;

@ServiceTest
class CreateTextAnswerRequestValidatorTest {

    @Autowired
    private CreateTextAnswerRequestValidator createTextAnswerRequestValidator;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void 저장되지_않은_질문에_대한_대답이면_예외가_발생한다() {
        // given
        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(100L, null, "텍스트형 응답");

        // when, then
        assertThatCode(() -> createTextAnswerRequestValidator.validate(request))
                .isInstanceOf(SubmittedQuestionNotFoundException.class);
    }

    @Test
    void 텍스트형_질문에_선택형_응답을_하면_예외가_발생한다() {
        // given
        Question savedQuestion
                = questionRepository.save(new Question(true, QuestionType.TEXT, "질문", "가이드라인", 1));
        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(savedQuestion.getId(), List.of(1L), "응답");

        // when, then
        assertThatCode(() -> createTextAnswerRequestValidator.validate(request))
                .isInstanceOf(TextAnswerIncludedOptionItemException.class);
    }

    @Test
    void 필수_텍스트형_질문에_응답을_하지_않으면_예외가_발생한다() {
        // given
        Question savedQuestion
                = questionRepository.save(new Question(true, QuestionType.TEXT, "질문", "가이드라인", 1));
        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(savedQuestion.getId(), null, null);

        // when, then
        assertThatCode(() -> createTextAnswerRequestValidator.validate(request))
                .isInstanceOf(MissingRequiredAnswerException.class);
    }
}
