package reviewme.review.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import reviewme.review.domain.exception.InvalidAnswerLengthException;

class ReviewContentTest {

    @ParameterizedTest
    @ValueSource(ints = {20, 1000})
    void 리뷰_답변이_범위에_해당하는_경우_올바르게_생성된다(int length) {
        // given
        Question question = new Question("Question 1");
        String content = "A".repeat(length);

        // when, then
        assertDoesNotThrow(() -> new ReviewContent(question, content));
    }

    @ParameterizedTest
    @ValueSource(ints = {19, 1001})
    void 리뷰_답변이_범위를_벗어나는_경우_예외를_발생한다(int length) {
        // given
        Question question = new Question("Question 1");
        String content = "A".repeat(length);

        // when, then
        assertThatThrownBy(() -> new ReviewContent(question, content))
                .isInstanceOf(InvalidAnswerLengthException.class);
    }
}
