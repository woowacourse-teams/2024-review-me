package reviewme.highlight.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import reviewme.highlight.domain.exception.HighlightIndexExceedLineLengthException;

@Getter
@EqualsAndHashCode
public class HighlightLine {

    private final int lineIndex;
    private final String lineContent;
    private final List<HighlightRange> ranges;

    public HighlightLine(int lineIndex, String lineContent) {
        this.lineIndex = lineIndex;
        this.lineContent = lineContent;
        this.ranges = new ArrayList<>();
    }

    public void addRange(int startIndex, int endIndex) {
        validateRangeByContentLength(startIndex, endIndex);
        ranges.add(new HighlightRange(startIndex, endIndex));
    }

    private void validateRangeByContentLength(long startIndex, long endIndex) {
        int providedEndIndex = lineContent.length() - 1;
        if (startIndex > providedEndIndex || endIndex > providedEndIndex) {
            throw new HighlightIndexExceedLineLengthException(lineContent.length(), startIndex, endIndex);
        }
    }

    public boolean hasDuplicatedRange(int startIndex, int endIndex) {
        return ranges.stream()
                .anyMatch(range -> range.equals(new HighlightRange(startIndex, endIndex)));
    }
}
