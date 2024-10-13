package reviewme.highlight.domain;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import reviewme.highlight.service.exception.InvalidHighlightLineIndexException;
import reviewme.review.domain.TextAnswer;

@Getter
public class HighlightWithAnswer {

    private long answerId;
    private List<HighlightLine> lines;

    public HighlightWithAnswer(TextAnswer answer, List<Integer> lineIndex) {
        this.answerId = answer.getId();
        lines = mapLines(answer, lineIndex);
    }

    private List<HighlightLine> mapLines(TextAnswer answer, List<Integer> lineIndexs) {
        long lineCount = answer.getContent().lines().count();
        List<String> lineGroup = Arrays.asList(answer.getContent().split("\n"));

        validateLineIndexRange(lineIndexs, lineCount);

        return lineIndexs.stream()
                .map(lineIndex -> new HighlightLine(lineIndex, lineGroup.get(lineIndex.intValue())))
                .toList();
    }

    private void validateLineIndexRange(List<Integer> lineIndexs, long lineCount) {
        for (long submittedLineIndex : lineIndexs) {
            if (submittedLineIndex > lineCount - 1) {
                throw new InvalidHighlightLineIndexException(submittedLineIndex, lineCount);
            }
        }
    }

    public void addRange(int lineIndex, int startIndex, int endIndex) {
        for (HighlightLine line : lines) { // TODO: Lines 분리 필요성
            if (line.getLineIndex() == lineIndex) {
                line.addRange(startIndex, endIndex);
            }
        }
    }
}
