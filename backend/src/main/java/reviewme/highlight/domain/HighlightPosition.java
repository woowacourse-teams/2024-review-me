package reviewme.highlight.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewme.highlight.domain.exception.HighlightStartIndexExceedEndIndexException;
import reviewme.highlight.domain.exception.NegativeHighlightIndexException;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class HighlightPosition {

    @Column(name = "line_index", nullable = false)
    private int lineIndex;

    @Column(name = "start_index", nullable = false)
    private int startIndex;

    @Column(name = "end_index", nullable = false)
    private int endIndex;

    public HighlightPosition(int lineIndex, int startIndex, int endIndex) {
        validateNonNegativeIndexNumber(startIndex, endIndex);
        validateEndIndexOverStartIndex(startIndex, endIndex);
        this.lineIndex = lineIndex;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    private void validateNonNegativeIndexNumber(long startIndex, long endIndex) {
        if (startIndex < 0 || endIndex < 0) {
            throw new NegativeHighlightIndexException(startIndex, endIndex);
        }
    }

    private void validateEndIndexOverStartIndex(long startIndex, long endIndex) {
        if (startIndex > endIndex) {
            throw new HighlightStartIndexExceedEndIndexException(startIndex, endIndex);
        }
    }
}
