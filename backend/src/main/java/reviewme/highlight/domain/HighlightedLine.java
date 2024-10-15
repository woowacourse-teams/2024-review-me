package reviewme.highlight.domain;

import java.util.HashSet;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import reviewme.highlight.domain.exception.HighlightIndexExceedLineLengthException;
import reviewme.highlight.entity.HighlightRange;

@Getter
@EqualsAndHashCode
public class HighlightedLine {

    private final int index;
    private final String lineContent;
    private final Set<HighlightRange> ranges;

    public HighlightedLine(int index, String lineContent) {
        this.index = index;
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
