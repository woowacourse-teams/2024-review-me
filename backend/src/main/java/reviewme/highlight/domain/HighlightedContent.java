package reviewme.highlight.domain;

import java.util.Arrays;
import java.util.List;

public class HighlightedContent {

    private static final String NEW_LINE_DELIMITER = "\n";

    private final List<HighlightedLine> lines;

    public HighlightedContent(String content) {
        this.lines = Arrays.stream(content.split(NEW_LINE_DELIMITER))
                .map(HighlightedLine::new)
                .toList();
    }

    public void addHighlightPosition(long lineIndex, long startIndex, long endIndex) {
        if (lineIndex < 0 || lineIndex >= lines.size()) {
            throw new IllegalArgumentException("Invalid line index: " + lineIndex);
        }
        HighlightedLine line = lines.get((int) lineIndex);
        line.addHighlightRange(startIndex, endIndex);
    }
}
