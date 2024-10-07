package reviewme.review.domain.abstraction;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import reviewme.review.domain.exception.QuestionNotAnsweredException;

class NewCheckboxAnswerTest {

    @Test
    void 답변이_없는_경우_예외를_발생한다() {
        // given, when, then
        assertAll(
                () -> assertThatThrownBy(() -> new NewCheckboxAnswer(1L, null))
                        .isInstanceOf(QuestionNotAnsweredException.class),
                () -> assertThatThrownBy(() -> new NewCheckboxAnswer(1L, List.of()))
                        .isInstanceOf(QuestionNotAnsweredException.class)
        );
    }
}