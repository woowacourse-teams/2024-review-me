package reviewme.reviewgroup;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import reviewme.global.exception.BadRequestException;
import reviewme.reviewgroup.domain.ReviewGroup;

class ReviewGroupTest {

    @Test
    void 정상_생성된다() {
        // given
        int maxLength = 50;
        int minLength = 1;
        String minLengthName = "*".repeat(minLength);
        String maxLengthName = "*".repeat(maxLength);

        // when, then
        assertAll(
                () -> assertThatCode(() -> new ReviewGroup(minLengthName, "project", "reviewCode", "groupCode"))
                        .doesNotThrowAnyException(),
                () -> assertThatCode(() -> new ReviewGroup(maxLengthName, "project", "reviewCode", "groupCode"))
                        .doesNotThrowAnyException()
        );
    }

    @Test
    void 리뷰이_이름이_정해진_길이에_맞지_않으면_예외가_발생한다() {
        // given
        int maxLength = 50;
        int minLength = 1;
        String insufficientName = "*".repeat(minLength - 1);
        String exceedName = "*".repeat(maxLength + 1);

        // when, then
        assertAll(
                () -> assertThatCode(() -> new ReviewGroup(insufficientName, "project", "reviewCode", "groupCode"))
                        .isInstanceOf(BadRequestException.class),
                () -> assertThatThrownBy(() -> new ReviewGroup(exceedName, "project", "reviewCode", "groupCode"))
                        .isInstanceOf(BadRequestException.class)
        );
    }

    @Test
    void 프로젝트_이름이_정해진_길이에_맞지_않으면_예외가_발생한다() {
        // given
        int maxLength = 50;
        int minLength = 1;
        String insufficientName = "*".repeat(minLength - 1);
        String exceedName = "*".repeat(maxLength + 1);

        // when, then
        assertAll(
                () -> assertThatThrownBy(() -> new ReviewGroup("reviwee", insufficientName, "reviewCode", "groupCode"))
                        .isInstanceOf(BadRequestException.class),
                () -> assertThatThrownBy(() -> new ReviewGroup("reviwee", exceedName, "reviewCode", "groupCode"))
                        .isInstanceOf(BadRequestException.class)
        );
    }
}
