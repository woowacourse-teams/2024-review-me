package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import reviewme.review.domain.ReviewContent;

class ReviewPreviewGeneratorTest {

    @Test
    void 답변_내용이_미리보기_최대_글자를_넘는_경우_미리보기_길이만큼_잘라서_반환한다() {
        // given
        ReviewPreviewGenerator reviewPreviewGenerator = new ReviewPreviewGenerator();
        String answer = "*".repeat(151);
        ReviewContent reviewContent = new ReviewContent(1L, answer);

        // when
        String actual = reviewPreviewGenerator.generatePreview(List.of(reviewContent));

        // then
        assertThat(actual).hasSize(150);
    }

    @ParameterizedTest
    @ValueSource(ints = {149, 150})
    void 답변_내용이_미리보기_최대_글자를_넘지_않는_경우_전체_내용을_반환한다(int length) {
        // given
        ReviewPreviewGenerator reviewPreviewGenerator = new ReviewPreviewGenerator();
        String answer = "*".repeat(length);
        ReviewContent reviewContent = new ReviewContent(1L, answer);

        // when
        String actual = reviewPreviewGenerator.generatePreview(List.of(reviewContent));

        // then
        assertThat(actual).hasSize(length);
    }
}
