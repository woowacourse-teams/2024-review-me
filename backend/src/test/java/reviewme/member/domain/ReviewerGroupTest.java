package reviewme.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import reviewme.member.domain.exception.DescriptionLengthExceededException;
import reviewme.member.domain.exception.InvalidGroupNameLengthException;

class ReviewerGroupTest {

    @Test
    void 리뷰_그룹이_올바르게_생성된다() {
        // given
        Member sancho = new Member("산초", 1);
        String groupName = "a".repeat(100);
        String description = "a".repeat(50);
        LocalDateTime createdAt = LocalDateTime.of(2024, 7, 17, 12, 0);

        // when, then
        assertDoesNotThrow(() -> new ReviewerGroup(sancho, groupName, description, createdAt));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 101})
    void 리뷰_그룹_이름_길이_제한을_벗어나는_경우_예외를_발생한다(int length) {
        // given
        String groupName = "a".repeat(length);
        Member sancho = new Member("산초", 1);
        LocalDateTime createdAt = LocalDateTime.of(2024, 7, 17, 12, 0);
        // when, then
        assertThatThrownBy(() -> new ReviewerGroup(sancho, groupName, "설명", createdAt))
                .isInstanceOf(InvalidGroupNameLengthException.class);
    }

    @Test
    void 리뷰_그룹_설명_길이_제한을_벗어나는_경우_예외를_발생한다() {
        // given
        String description = "a".repeat(51);
        Member sancho = new Member("산초", 1);
        LocalDateTime createdAt = LocalDateTime.of(2024, 7, 17, 12, 0);
        // when, then
        assertThatThrownBy(() -> new ReviewerGroup(sancho, "그룹 이름", description, createdAt))
                .isInstanceOf(DescriptionLengthExceededException.class);
    }
}
