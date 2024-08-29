package reviewme.review.service.module;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.fixture.QuestionFixture;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.exception.InvalidTextAnswerLengthException;
import reviewme.review.service.exception.SubmittedQuestionNotFoundException;
import reviewme.support.ServiceTest;

@ServiceTest
class TextAnswerValidatorTest {

    @Autowired
    private TextAnswerValidator textAnswerValidator;

    @Autowired
    private QuestionRepository questionRepository;

    private QuestionFixture questionFixture = new QuestionFixture();

    @Test
    void 저장되지_않은_질문에_대한_대답이면_예외가_발생한다() {
        // given
        long notSavedQuestionId = 100L;
        TextAnswer textAnswer = new TextAnswer(notSavedQuestionId, "텍스트형 응답");

        // when, then
        assertThatCode(() -> textAnswerValidator.validate(textAnswer))
                .isInstanceOf(SubmittedQuestionNotFoundException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {19, 10001})
    void 답변_길이가_유효하지_않으면_예외가_발생한다(int length) {
        // given
        String content = "답".repeat(length);
        Question savedQuestion = questionRepository.save(questionFixture.서술형_필수_질문());
        TextAnswer textAnswer = new TextAnswer(savedQuestion.getId(), content);

        // when, then
        assertThatThrownBy(() -> textAnswerValidator.validate(textAnswer))
                .isInstanceOf(InvalidTextAnswerLengthException.class);
    }
}
