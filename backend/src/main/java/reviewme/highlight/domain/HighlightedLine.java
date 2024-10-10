package reviewme.highlight.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HighlightedLine {

    private final String content;
    private List<HighlightRange> highlightedRanges = new ArrayList<>();

    public void addHighlightRange(long startIndex, long endIndex) {
        if (startIndex < 0 || startIndex >= content.length() || endIndex >= content.length()) {
            throw new IllegalArgumentException("Invalid start index: " + startIndex);
        }
        highlightedRanges.add(new HighlightRange(startIndex, endIndex));
    }
}
