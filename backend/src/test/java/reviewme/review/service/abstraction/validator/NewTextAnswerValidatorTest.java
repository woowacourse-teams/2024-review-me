package reviewme.review.service.abstraction.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static reviewme.fixture.QuestionFixture.서술형_옵션_질문;
import static reviewme.fixture.QuestionFixture.서술형_필수_질문;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.abstraction.NewTextAnswer;
import reviewme.review.service.exception.InvalidTextAnswerLengthException;
import reviewme.review.service.exception.SubmittedQuestionNotFoundException;
import reviewme.support.ServiceTest;

@ServiceTest
class NewTextAnswerValidatorTest {

    @Autowired
    private NewTextAnswerValidator textAnswerValidator;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void 저장되지_않은_질문에_대한_대답이면_예외가_발생한다() {
        // given
        long notSavedQuestionId = 100L;
        NewTextAnswer textAnswer = new NewTextAnswer(notSavedQuestionId, "텍스트형 응답");

        // when, then
        assertThatThrownBy(() -> textAnswerValidator.validate(textAnswer))
                .isInstanceOf(SubmittedQuestionNotFoundException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {19, 10001})
    void 필수_질문의_답변_길이가_유효하지_않으면_예외가_발생한다(int length) {
        // given
        String content = "답".repeat(length);
        Question savedQuestion = questionRepository.save(서술형_필수_질문());
        NewTextAnswer textAnswer = new NewTextAnswer(savedQuestion.getId(), content);

        // when, then
        assertThatThrownBy(() -> textAnswerValidator.validate(textAnswer))
                .isInstanceOf(InvalidTextAnswerLengthException.class);
    }

    @Test
    void 선택_질문의_답변_길이가_유효하지_않으면_예외가_발생한다() {
        // given
        String content = "답".repeat(10001);
        Question savedQuestion = questionRepository.save(서술형_옵션_질문());
        NewTextAnswer textAnswer = new NewTextAnswer(savedQuestion.getId(), content);

        // when, then
        assertThatThrownBy(() -> textAnswerValidator.validate(textAnswer))
                .isInstanceOf(InvalidTextAnswerLengthException.class);
    }

    @Test
    void 선택_질문은_최소_글자수_제한을_받지_않는다() {
        // given
        String content = "답".repeat(1);
        Question savedQuestion = questionRepository.save(서술형_옵션_질문());
        NewTextAnswer textAnswer = new NewTextAnswer(savedQuestion.getId(), content);

        // when, then
        assertDoesNotThrow(() -> textAnswerValidator.validate(textAnswer));
    }
}