package reviewme.reviewgroup.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import reviewme.reviewgroup.domain.exception.InvalidGroupAccessCodeFormatException;

class GroupAccessCodeTest {

    @Test
    void 코드_일치_여부를_판단한다() {
        // given
        String code = "hello";
        GroupAccessCode groupAccessCode = new GroupAccessCode(code);

        // when, then
        assertThat(groupAccessCode.matches("hello")).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"AZaz", "a0Z9", "aZ09", "ABCD123a", "1234"})
    void 정규식에_일치하면_성공적으로_생성된다(String code) {
        assertDoesNotThrow(() -> new GroupAccessCode(code));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "123456789012345678901", "aaaa-"})
    void 정규식에_일치하지_않으면_예외가_발생한다(String code) {
        assertThrows(InvalidGroupAccessCodeFormatException.class, () -> new GroupAccessCode(code));
    }
}
