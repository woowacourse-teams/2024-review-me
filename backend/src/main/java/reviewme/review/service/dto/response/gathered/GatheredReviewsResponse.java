package reviewme.review.service.dto.response.gathered;

import java.util.List;

public record GatheredReviewsResponse(
        List<GatheredReviewResponse> reviews
) {
}
