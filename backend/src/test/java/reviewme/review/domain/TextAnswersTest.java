package reviewme.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import reviewme.review.domain.exception.MissingTextAnswerForQuestionException;

class TextAnswersTest {

    @Test
    void 질문에_해당하는_답변이_없으면_예외를_발생한다() {
        // given
        TextAnswers textAnswers = new TextAnswers(List.of(new NewTextAnswer(1, "답".repeat(20))));

        // when, then
        assertThatThrownBy(() -> textAnswers.getAnswerByQuestionId(2))
                .isInstanceOf(MissingTextAnswerForQuestionException.class);
    }

    @Test
    void 질문_ID로_서술형_답변을_반환한다() {
        // given
        TextAnswers textAnswers = new TextAnswers(List.of(new NewTextAnswer(1, "답".repeat(20))));

        // when
        NewTextAnswer actual = textAnswers.getAnswerByQuestionId(1);

        // then
        assertThat(actual.getContent()).isEqualTo("답".repeat(20));
    }

    @Test
    void 질문_ID에_해당하는_답변이_있는지_확인한다() {
        // given
        TextAnswers textAnswers = new TextAnswers(List.of(new NewTextAnswer(1, "답변")));

        // when
        boolean actual1 = textAnswers.hasAnswerByQuestionId(1);
        boolean actual2 = textAnswers.hasAnswerByQuestionId(2);

        // then
        assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }
}
