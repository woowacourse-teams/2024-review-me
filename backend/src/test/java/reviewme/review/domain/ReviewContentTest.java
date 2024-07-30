package reviewme.review.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import reviewme.global.exception.BadRequestException;

class ReviewContentTest {


    @Test
    void 정상_생성된다() {
        // given
        int minLength = 20;
        int maxLength = 1000;
        String minLengthAnswer = "*".repeat(minLength);
        String maxLengthAnswer = "*".repeat(maxLength);

        // when, then
        assertAll(
                () -> assertThatCode(() -> new ReviewContent(1L, minLengthAnswer))
                        .doesNotThrowAnyException(),
                () -> assertThatCode(() -> new ReviewContent(1L, maxLengthAnswer))
                        .doesNotThrowAnyException()
        );
    }

    @Test
    void 답변이_정해진_길이에_맞지_않으면_예외가_발생한다() {
        // given
        int minLength = 20;
        int maxLength = 1000;
        String insufficientLengthAnswer = "*".repeat(minLength - 1);
        String exceedLengthAnswer = "*".repeat(maxLength + 1);

        // when, then
        assertAll(
                () -> assertThatCode(() -> new ReviewContent(1L, insufficientLengthAnswer))
                        .isInstanceOf(BadRequestException.class),
                () -> assertThatCode(() -> new ReviewContent(1L, exceedLengthAnswer))
                        .isInstanceOf(BadRequestException.class)
        );
    }
}
