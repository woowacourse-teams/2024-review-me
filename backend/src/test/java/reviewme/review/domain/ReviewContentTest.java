package reviewme.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    @Test
    void 답변_내용이_미리보기_최대_글자를_넘는_경우_미리보기_길이만큼_잘라서_반환한다() {
        // given
        String answer = "*".repeat(151);
        ReviewContent reviewContent = new ReviewContent(1L, answer);

        // when
        String actual = reviewContent.getAnswerPreview();

        // then
        assertThat(actual).hasSize(150);
    }

    @ParameterizedTest
    @ValueSource(ints = {149, 150})
    void 답변_내용이_미리보기_최대_글자를_넘지_않는_경우_전체_내용을_반환한다(int length) {
        // given
        String answer = "*".repeat(length);
        ReviewContent reviewContent = new ReviewContent(1L, answer);

        // when
        String actual = reviewContent.getAnswerPreview();

        // then
        assertThat(actual).hasSize(length);
    }
}
