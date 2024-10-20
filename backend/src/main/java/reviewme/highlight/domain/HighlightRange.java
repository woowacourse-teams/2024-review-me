package reviewme.highlight.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewme.highlight.domain.exception.InvalidHighlightIndexRangeException;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class HighlightRange {

    @Column(name = "start_index", nullable = false)
    private int startIndex;

    @Column(name = "end_index", nullable = false)
    private int endIndex;

    public HighlightRange(int startIndex, int endIndex) {
        validateNonNegativeIndexNumber(startIndex, endIndex);
        validateEndIndexOverStartIndex(startIndex, endIndex);
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    private void validateNonNegativeIndexNumber(int startIndex, int endIndex) {
        if (startIndex < 0 || endIndex < 0) {
            throw new InvalidHighlightIndexRangeException(startIndex, endIndex);
        }
    }

    private void validateEndIndexOverStartIndex(int startIndex, int endIndex) {
        if (startIndex > endIndex) {
            throw new InvalidHighlightIndexRangeException(startIndex, endIndex);
        }
    }
}
