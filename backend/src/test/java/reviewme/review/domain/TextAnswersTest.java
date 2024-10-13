package reviewme.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import reviewme.review.service.exception.AnswerNotFoundByIdException;

class TextAnswersTest {

    @Test
    void 답변_id에_해당하는_답변이_없으면_예외를_발생한다() {
        // given
        TextAnswers textAnswers = new TextAnswers(List.of(new TextAnswer(1, 1, "답".repeat(20))));

        // when, then
        assertThatThrownBy(() -> textAnswers.get(2))
                .isInstanceOf(AnswerNotFoundByIdException.class);
    }

    @Test
    void 답변_id로_서술형_답변을_반환한다() {
        // given
        TextAnswers textAnswers = new TextAnswers(List.of(new TextAnswer(1, 1, "답".repeat(20))));

        // when
        TextAnswer actual = textAnswers.get(1);

        // then
        assertThat(actual.getContent()).isEqualTo("답".repeat(20));
    }
}
