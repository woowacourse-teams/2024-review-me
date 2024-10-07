package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.mapper.ReviewPreviewGenerator;

class ReviewPreviewGeneratorTest {

    @Test
    void 답변_내용이_미리보기_최대_글자를_넘는_경우_미리보기_길이만큼_자르고_말줄임표를_붙여_반환한다() {
        // given
        ReviewPreviewGenerator reviewPreviewGenerator = new ReviewPreviewGenerator();
        String answer = "*".repeat(151);
        TextAnswer textAnswer = new TextAnswer(1, answer);

        // when
        String actual = reviewPreviewGenerator.generatePreview(List.of(textAnswer));

        // then
        assertThat(actual).isEqualTo("*".repeat(150) + "...");
    }

    @ParameterizedTest
    @ValueSource(ints = {149, 150})
    void 답변_내용이_미리보기_최대_글자를_넘지_않는_경우_전체_내용을_반환한다(int length) {
        // given
        ReviewPreviewGenerator reviewPreviewGenerator = new ReviewPreviewGenerator();
        String answer = "*".repeat(length);
        TextAnswer textAnswer = new TextAnswer(1, answer);

        // when
        String actual = reviewPreviewGenerator.generatePreview(List.of(textAnswer));

        // then
        assertThat(actual).hasSize(length);
    }
}
