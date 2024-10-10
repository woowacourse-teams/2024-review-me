package reviewme.highlight.service.validator;

import java.util.Objects;
import lombok.Getter;

@Getter
public class HighlightKey {

    private final Long answerId;
    private final Long lineIndex;
    private final Long startIndex;
    private final Long endIndex;

    public HighlightKey(Long answerId, Long lineIndex, Long startIndex, Long endIndex) {
        this.answerId = answerId;
        this.lineIndex = lineIndex;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HighlightKey that = (HighlightKey) o;
        return answerId.equals(that.answerId) && lineIndex.equals(that.lineIndex) &&
               startIndex.equals(that.startIndex) && endIndex.equals(that.endIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId, lineIndex, startIndex, endIndex);
    }
}
