package reviewme.highlight.domain;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import reviewme.highlight.service.exception.InvalidHighlightLineIndexException;
import reviewme.review.domain.TextAnswer;

@Getter
public class HighlightLines {

    private final List<HighlightLine> lines;

    public HighlightLines(TextAnswer answer, List<Integer> lineIndexs) {
        validateLineIndexRange(lineIndexs, answer.getContent().lines().count());
        this.lines = mapLines(answer, lineIndexs);
    }

    public void addRange(int lineIndex, int startIndex, int endIndex) {
        HighlightLine line = lines.get(lineIndex);
        line.addRange(startIndex, endIndex);
    }

    public boolean hasDuplicatedRange(int lineIndex, int startIndex, int endIndex) {
        HighlightLine line = lines.get(lineIndex);
        return line.hasDuplicatedRange(startIndex, endIndex);
    }

    private void validateLineIndexRange(List<Integer> lineIndexs, long lineCount) {
        for (long submittedLineIndex : lineIndexs) {
            if (submittedLineIndex > lineCount - 1) {
                throw new InvalidHighlightLineIndexException(submittedLineIndex, lineCount);
            }
        }
    }

    private List<HighlightLine> mapLines(TextAnswer answer, List<Integer> lineIndexs) {
        List<String> lineGroup = Arrays.asList(answer.getContent().split("\n"));
        return lineIndexs.stream()
                .map(lineIndex -> new HighlightLine(lineIndex, lineGroup.get(lineIndex)))
                .toList();
    }
}
