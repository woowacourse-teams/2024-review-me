package reviewme.review.service.abstraction.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reviewme.review.domain.abstraction.Answer;
import reviewme.review.domain.abstraction.NewCheckboxAnswer;

class AnswerValidatorFactoryTest {

    private final NewAnswerValidator validator = new NewAnswerValidator() {

        @Override
        public boolean supports(Class<? extends Answer> answerClass) {
            return answerClass.equals(NewCheckboxAnswer.class);
        }

        @Override
        public void validate(Answer answer) {
        }
    };

    @Test
    @DisplayName("지원하는 타입에 따른 밸리데이터를 가져온다.")
    void getValidatorByAnswerType() {
        // given
        List<NewAnswerValidator> validators = List.of(validator);
        AnswerValidatorFactory factory = new AnswerValidatorFactory(validators);

        // when
        NewAnswerValidator actual = factory.getAnswerValidator(NewCheckboxAnswer.class);

        // then
        assertThat(actual).isEqualTo(validator);
    }

    @Test
    @DisplayName("지원하지 않는 타입에 대한 밸리데이터 요청 시 예외가 발생한다.")
    void unsupportedAnswerType() {
        // given
        AnswerValidatorFactory factory = new AnswerValidatorFactory(List.of());

        // when, then
        assertThatThrownBy(() -> factory.getAnswerValidator(NewCheckboxAnswer.class))
                .isInstanceOf(UnsupportedAnswerTypeException.class);
    }
}
