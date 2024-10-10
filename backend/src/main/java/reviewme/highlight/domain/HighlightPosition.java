package reviewme.highlight.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import reviewme.highlight.domain.exception.InvalidHighlightRangeException;

@Getter
@EqualsAndHashCode
public class HighlightPosition {

    private final long lineIndex;
    private final long startIndex;
    private final long endIndex;

    public HighlightPosition(long lineIndex, long startIndex, long endIndex) {
        validateRange(startIndex, endIndex);
        this.lineIndex = lineIndex;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    private void validateRange(long startIndex, long endIndex) {
        if (startIndex > endIndex) {
            throw new InvalidHighlightRangeException(startIndex, endIndex);
        }
    }
}
