package reviewme.highlight.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class HighlightLine {

    private final int lineIndex;
    private final String lineContent;
    private List<HighlightRange> ranges;

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
            throw new IllegalArgumentException(); // TODO: 예외 구체화
        }

    }

    public boolean hasDuplicatedRange(int startIndex, int endIndex) {
        return ranges.stream().anyMatch(range -> range.isSameRange(startIndex, endIndex));
    }
}
