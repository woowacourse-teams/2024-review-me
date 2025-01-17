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
public class SelectionRange {

    @Column(name = "min_selection_count", nullable = false)
    private int minSelectionCount;

    @Column(name = "max_selection_count", nullable = false)
    private int maxSelectionCount;

    public SelectionRange(int minSelectionCount, int maxSelectionCount) {
        if (minSelectionCount < 0 || minSelectionCount > maxSelectionCount) {
            throw new InvalidSelectionRangeException(minSelectionCount, maxSelectionCount);
        }
        this.minSelectionCount = minSelectionCount;
        this.maxSelectionCount = maxSelectionCount;
    }

    public boolean isOutOfRange(int selectionCount) {
        return selectionCount < minSelectionCount || selectionCount > maxSelectionCount;
    }
}
