package reviewme.review.service.dto.response.list;

import java.util.List;

public record AuthoredReviewsResponse(
        List<AuthoredReviewElementResponse> reviews,
        long lastReviewId,
        boolean isLastPage
) {
}
