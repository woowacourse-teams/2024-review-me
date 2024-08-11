package reviewme.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import reviewme.review.domain.exception.TextAnswerNotFoundException;

class TextAnswersTest {

    @Test
    void 질문에_해당하는_답변이_없으면_예외를_발생한다() {
        // given
        TextAnswers textAnswers = new TextAnswers(List.of(new TextAnswer(1, "답변")));

        // when, then
        assertThatThrownBy(() -> textAnswers.getAnswerByQuestionId(2))
                .isInstanceOf(TextAnswerNotFoundException.class);
    }

    @Test
    void 질문_ID로_서술형_답변을_반환한다() {
        // given
        TextAnswers textAnswers = new TextAnswers(List.of(new TextAnswer(1, "답변")));

        // when
        TextAnswer actual = textAnswers.getAnswerByQuestionId(1);

        // then
        assertThat(actual.getContent()).isEqualTo("답변");
    }
}
