package reviewme.highlight.domain;

import java.util.List;
import lombok.Getter;
import reviewme.highlight.service.exception.HighlightDuplicatedException;
import reviewme.review.domain.TextAnswer;

@Getter
public class HighlightContent {

    private final long answerId;
    private final HighlightLines lines;

    public HighlightContent(TextAnswer answer, List<Integer> lineIndex) {
        this.answerId = answer.getId();
        lines = new HighlightLines(answer, lineIndex);
    }

    public void addRange(int lineIndex, int startIndex, int endIndex) {
        validateDuplicate(lineIndex, startIndex, endIndex);
        lines.addRange(lineIndex, startIndex, endIndex);
    }

    private void validateDuplicate(int lineIndex, int startIndex, int endIndex) {
        if (lines.hasDuplicatedRange(lineIndex, startIndex, endIndex)) {
            throw new HighlightDuplicatedException(answerId, lineIndex, startIndex, endIndex);
        }
    }

    public List<HighlightLine> getLines() {
        return lines.getLines();
    }
}
