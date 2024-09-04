package reviewme.review.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import reviewme.review.service.exception.QuestionNotAnsweredException;

class CheckboxAnswerTest {

    @Test
    void 답변이_null_인_경우_예외를_발생한다() {
        // given, when, then
        assertThatThrownBy(() -> new CheckboxAnswer(1L, null))
                .isInstanceOf(QuestionNotAnsweredException.class);
    }
}
