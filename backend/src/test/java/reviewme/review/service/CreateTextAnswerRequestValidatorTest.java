package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static reviewme.fixture.QuestionFixture.꼬리_질문_서술형;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.question.domain.Question;
import reviewme.question.domain.exception.QuestionNotFoundException;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.service.dto.request.CreateReviewAnswerRequest;
import reviewme.review.service.exception.MissingRequiredQuestionAnswerException;
import reviewme.review.service.exception.TextAnswerInculdedOptionException;
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
        long notSavedQuestionId = 100L;
        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(notSavedQuestionId, null, "텍스트형 응답");

        // when, then
        assertThatCode(() -> createTextAnswerRequestValidator.validate(request))
                .isInstanceOf(QuestionNotFoundException.class);
    }

    @Test
    void 텍스트형_질문에_선택형_응답을_하면_예외가_발생한다() {
        // given
        List<Long> selectedOptionIds = List.of(1L);
        Question question = questionRepository.save(꼬리_질문_서술형.create());
        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(question.getId(), selectedOptionIds, "응답");

        // when, then
        assertThatCode(() -> createTextAnswerRequestValidator.validate(request))
                .isInstanceOf(TextAnswerInculdedOptionException.class);
    }

    @Test
    void 필수_텍스트형_질문에_응답을_하지_않으면_예외가_발생한다() {
        // given
        Question question = questionRepository.save(꼬리_질문_서술형.create());
        CreateReviewAnswerRequest request = new CreateReviewAnswerRequest(question.getId(), null, null);

        // when, then
        assertThatCode(() -> createTextAnswerRequestValidator.validate(request))
                .isInstanceOf(MissingRequiredQuestionAnswerException.class);
    }
}
