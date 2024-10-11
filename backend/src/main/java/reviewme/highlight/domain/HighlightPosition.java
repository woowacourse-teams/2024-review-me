package reviewme.highlight.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewme.highlight.domain.exception.InvalidHighlightRangeException;
import reviewme.highlight.domain.exception.HighlightStartIndexExceedEndIndexException;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
public class HighlightPosition {

    @Column(name = "line_index", nullable = false)
    private long lineIndex;

    @Column(name = "start_index", nullable = false)
    private long startIndex;

    @Column(name = "end_index", nullable = false)
    private long endIndex;

    public HighlightPosition(long lineIndex, long startIndex, long endIndex) {
        validateRange(startIndex, endIndex);
        validateEndIndexOverStartIndex(startIndex, endIndex);
        this.lineIndex = lineIndex;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    private void validateRange(long startIndex, long endIndex) {
        if (startIndex > endIndex) {
            throw new HighlightStartIndexExceedEndIndexException(startIndex, endIndex);
        }
    }
}
