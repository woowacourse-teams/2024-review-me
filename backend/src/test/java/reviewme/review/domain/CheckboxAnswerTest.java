package reviewme.review.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import reviewme.review.domain.exception.QuestionNotAnsweredException;

class CheckboxAnswerTest {

    @Test
    void 답변이_없는_경우_예외를_발생한다() {
        // given, when, then
        assertAll(
                () -> assertThatThrownBy(() -> new CheckboxAnswer(1L, null))
                .isInstanceOf(QuestionNotAnsweredException.class),
                () -> assertThatThrownBy(() -> new CheckboxAnswer(1L, List.of()))
                        .isInstanceOf(QuestionNotAnsweredException.class)
        );
    }
}
