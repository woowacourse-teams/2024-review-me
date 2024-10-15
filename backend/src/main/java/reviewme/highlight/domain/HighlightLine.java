package reviewme.highlight.domain;

import java.util.HashSet;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import reviewme.highlight.domain.exception.HighlightIndexExceedLineLengthException;
import reviewme.highlight.entity.HighlightRange;

@Getter
@EqualsAndHashCode
public class HighlightLine {

    private final int lineIndex;
    private final String lineContent;
    private final Set<HighlightRange> ranges;

    public HighlightLine(int lineIndex, String lineContent) {
        this.lineIndex = lineIndex;
        this.lineContent = lineContent;
        this.ranges = new HashSet<>();
    }

    public void setRange(int startIndex, int endIndex) {
        validateRangeByContentLength(startIndex, endIndex);
        ranges.add(new HighlightRange(startIndex, endIndex));
    }

    private void validateRangeByContentLength(int startIndex, int endIndex) {
        int providedEndIndex = lineContent.length() - 1;
        if (startIndex > providedEndIndex || endIndex > providedEndIndex) {
            throw new HighlightIndexExceedLineLengthException(lineContent.length(), startIndex, endIndex);
        }
    }
}
