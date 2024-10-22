package reviewme.review.service.dto.response.gathered;

import reviewme.highlight.domain.HighlightRange;

public record RangeResponse(
        long startIndex,
        long endIndex
) {

    public static RangeResponse from(HighlightRange range) {
        return new RangeResponse(range.getStartIndex(), range.getEndIndex());
    }
}
