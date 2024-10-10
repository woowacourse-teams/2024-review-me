package reviewme.review.service.dto.response.gathered;

import java.util.List;

public record TextResponse(
        long id,
        String content,
        List<HighlightResponse> highlights
) {
}
