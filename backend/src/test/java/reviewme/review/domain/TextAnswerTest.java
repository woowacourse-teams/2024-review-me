package reviewme.review.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import reviewme.review.domain.exception.InvalidTextAnswerLengthException;

class TextAnswerTest {

    @ParameterizedTest
    @ValueSource(ints = {20, 1000})
    void 답변_길이가_유효하면_정상_생성된다(int length) {
        // given
        String answer = "*".repeat(length);

        // when & then
        assertThatCode(() -> new TextAnswer(1L, answer))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {19, 10001})
    void 답변_길이가_유효하지_않으면_예외를_발생한다(int length) {
        // given
        String answer = "*".repeat(length);

        // when & then
        assertThatThrownBy(() -> new TextAnswer(1L, answer))
                .isInstanceOf(InvalidTextAnswerLengthException.class);
    }
}
