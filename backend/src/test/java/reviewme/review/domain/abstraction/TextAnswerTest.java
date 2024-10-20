package reviewme.review.domain.abstraction;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.exception.QuestionNotAnsweredException;

class TextAnswerTest {

    @Test
    void 답변이_없는_경우_예외를_발생한다() {
        // given, when, then
        assertAll(
                () -> assertThatThrownBy(() -> new TextAnswer(1L, null))
                        .isInstanceOf(QuestionNotAnsweredException.class),
                () -> assertThatThrownBy(() -> new TextAnswer(1L, ""))
                        .isInstanceOf(QuestionNotAnsweredException.class)
        );
    }
}
