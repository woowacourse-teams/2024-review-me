package reviewme.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import reviewme.review.domain.exception.MissingCheckboxAnswerForQuestionException;

class CheckboxAnswersTest {

    @Test
    void 질문에_해당하는_답변이_없으면_예외를_발생한다() {
        // given
        CheckboxAnswers checkboxAnswers = new CheckboxAnswers(List.of(new CheckboxAnswer(1, List.of(1L))));

        // when, then
        assertThatThrownBy(() -> checkboxAnswers.getAnswerByQuestionId(2))
                .isInstanceOf(MissingCheckboxAnswerForQuestionException.class);
    }

    @Test
    void 질문_ID로_서술형_답변을_반환한다() {
        // given
        CheckboxAnswers checkboxAnswers = new CheckboxAnswers(List.of(new CheckboxAnswer(1, List.of(1L))));

        // when
        CheckboxAnswer actual = checkboxAnswers.getAnswerByQuestionId(1);

        // then
        assertThat(actual.getSelectedOptionIds())
                .extracting(CheckBoxAnswerSelectedOption::getSelectedOptionId)
                .containsExactly(1L);
    }

    @Test
    void 질문_ID에_해당하는_답변이_있는지_확인한다() {
        // given
        CheckboxAnswers checkboxAnswers = new CheckboxAnswers(List.of(new CheckboxAnswer(1, List.of(1L))));

        // when
        boolean actual1 = checkboxAnswers.hasAnswerByQuestionId(1);
        boolean actual2 = checkboxAnswers.hasAnswerByQuestionId(2);

        // then
        assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }
}
