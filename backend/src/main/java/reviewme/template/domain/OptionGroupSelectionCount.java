package reviewme.template.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class OptionGroupSelectionCount {

    @Column(name = "min_selection_count", nullable = false)
    private int minSelectionCount;

    @Column(name = "max_selection_count", nullable = false)
    private int maxSelectionCount;

    public OptionGroupSelectionCount(int minSelectionCount, int maxSelectionCount) {
        if (minSelectionCount > maxSelectionCount) {
            throw new IllegalArgumentException("선택 가능 범위가 잘못 설정되었습니다.");
        }
        if (minSelectionCount <= 0) {
            throw new IllegalArgumentException("선택 가능 개수는 0보다 커야 합니다.");
        }
        this.minSelectionCount = minSelectionCount;
        this.maxSelectionCount = maxSelectionCount;
    }

    public boolean isOutOfRange(int selectionCount) {
        return selectionCount < minSelectionCount || selectionCount > maxSelectionCount;
    }
}
