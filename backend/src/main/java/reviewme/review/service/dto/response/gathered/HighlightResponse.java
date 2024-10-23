package reviewme.review.service.dto.response.gathered;

import java.util.List;

public record HighlightResponse(
        long lineIndex,
        List<RangeResponse> ranges
) {
}
