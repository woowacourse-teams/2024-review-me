package reviewme.highlight.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import reviewme.highlight.domain.exception.NegativeHighlightLineIndexException;
import reviewme.highlight.domain.exception.InvalidHighlightLineIndexException;

@Getter
public class HighlightLines {

    public static final String LINE_SEPARATOR = "\n";

    private final List<HighlightLine> lines;

    public HighlightLines(String content) {
        this.lines = mapLines(content);
    }

    public void setRange(int lineIndex, int startIndex, int endIndex) {
        validateNonNegativeLineIndexNumber(lineIndex);
        validateLineIndexRange(lineIndex);
        HighlightLine line = lines.get(lineIndex);
        line.setRange(startIndex, endIndex);
    }

    private void validateNonNegativeLineIndexNumber(int lineIndex) {
        if (lineIndex < 0) {
            throw new NegativeHighlightLineIndexException(lineIndex);
        }
    }

    private void validateLineIndexRange(int lineIndex) {
        if (lineIndex >= lines.size()) {
            throw new InvalidHighlightLineIndexException(lineIndex, lines.size());
        }
    }

    private List<HighlightLine> mapLines(String content) {
        List<HighlightLine> mappedLines = new ArrayList<>();
        String[] lineContents = content.split(LINE_SEPARATOR);
        for (int i = 0; i < lineContents.length; i++) {
            mappedLines.add(new HighlightLine(i , lineContents[i]));
        }
        return mappedLines;
    }
}
