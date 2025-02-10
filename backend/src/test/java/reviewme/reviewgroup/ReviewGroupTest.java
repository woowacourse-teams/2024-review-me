package reviewme.reviewgroup;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import reviewme.global.exception.BadRequestException;
import reviewme.reviewgroup.domain.ReviewGroup;

class ReviewGroupTest {

    @Test
    void 회원의_리뷰_그룹을_생성한다() {
        // given
        long memberId = 1L;
        int maxLength = 50;
        int minLength = 1;
        String minLengthName = "*".repeat(minLength);
        String maxLengthName = "*".repeat(maxLength);

        // when, then
        assertAll(
                () -> assertThatCode(() -> new ReviewGroup(memberId, 1L, minLengthName, "project", "reviewCode"))
                        .doesNotThrowAnyException(),
                () -> assertThatCode(() -> new ReviewGroup(memberId, 1L, maxLengthName, "project", "reviewCode"))
                        .doesNotThrowAnyException()
        );
    }

    @Test
    void 비회원의_리뷰_그룹을_생성한다() {
        // given
        int maxLength = 50;
        int minLength = 1;
        String minLengthName = "*".repeat(minLength);
        String maxLengthName = "*".repeat(maxLength);
        String groupAccessCode = "groupAccessCode";

        // when, then
        assertAll(
                () -> assertThatCode(() -> new ReviewGroup(1L, minLengthName, "project", "reviewCode", groupAccessCode))
                        .doesNotThrowAnyException(),
                () -> assertThatCode(() -> new ReviewGroup(1L, maxLengthName, "project", "reviewCode", groupAccessCode))
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
                () -> assertThatCode(() -> new ReviewGroup(1L, insufficientName, "project", "reviewCode", "groupCode"))
                        .isInstanceOf(BadRequestException.class),
                () -> assertThatThrownBy(() -> new ReviewGroup(1L, exceedName, "project", "reviewCode", "groupCode"))
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
                () -> assertThatThrownBy(() -> new ReviewGroup(1L, "reviwee", insufficientName, "reviewCode", "groupCode"))
                        .isInstanceOf(BadRequestException.class),
                () -> assertThatThrownBy(() -> new ReviewGroup(1L, "reviwee", exceedName, "reviewCode", "groupCode"))
                        .isInstanceOf(BadRequestException.class)
        );
    }
}
