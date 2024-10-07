package reviewme.review.service.abstraction.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
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
    void 지원하는_타입에_따른_밸리데이터를_가져온다() {
        // given
        List<NewAnswerValidator> validators = List.of(validator);
        AnswerValidatorFactory factory = new AnswerValidatorFactory(validators);

        // when
        NewAnswerValidator actual = factory.getAnswerValidator(NewCheckboxAnswer.class);

        // then
        assertThat(actual).isEqualTo(validator);
    }

    @Test
    void 지원하지_않는_타입에_대한_밸리데이터_요청_시_예외가_발생한다() {
        // given
        AnswerValidatorFactory factory = new AnswerValidatorFactory(List.of());

        // when, then
        assertThatThrownBy(() -> factory.getAnswerValidator(NewCheckboxAnswer.class))
                .isInstanceOf(UnsupportedAnswerTypeException.class);
    }
}
