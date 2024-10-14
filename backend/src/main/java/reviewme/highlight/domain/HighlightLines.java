package reviewme.highlight.domain;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import reviewme.highlight.service.exception.InvalidHighlightLineIndexException;

@Getter
public class HighlightLines {

    public static final String LINE_SEPARATOR = "\n";
    private final List<HighlightLine> lines;

    public HighlightLines(String content, List<Integer> lineIndexes) {
        validateLineIndexRange(lineIndexes, content.lines().count());
        this.lines = mapLines(content, lineIndexes);
    }

    public void addRange(int lineIndex, int startIndex, int endIndex) {
        HighlightLine highlightLine = lines.stream()
                .filter(line -> line.getLineIndex() == lineIndex)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException()); // TODO: 예외 구체화
        highlightLine.addRange(startIndex, endIndex);
    }

    public boolean hasDuplicatedRange(int lineIndex, int startIndex, int endIndex) {
        HighlightLine line = lines.get(lineIndex);
        return line.hasDuplicatedRange(startIndex, endIndex);
    }

    private void validateLineIndexRange(List<Integer> lineIndexes, long lineCount) {
        for (long submittedLineIndex : lineIndexes) {
            if (submittedLineIndex > lineCount - 1) {
                throw new InvalidHighlightLineIndexException(submittedLineIndex, lineCount);
            }
        }
    }

    private List<HighlightLine> mapLines(String content, List<Integer> lineIndexes) {
        List<String> lineGroup = Arrays.asList(content.split(LINE_SEPARATOR));
        return lineIndexes.stream()
                .map(lineIndex -> new HighlightLine(lineIndex, lineGroup.get(lineIndex)))
                .toList();
    }
}
