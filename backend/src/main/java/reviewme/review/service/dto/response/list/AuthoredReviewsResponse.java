package reviewme.review.service.dto.response.list;

import java.util.List;

public record AuthoredReviewsResponse(

        long lastReviewId,
        boolean isLastPage,
        List<AuthoredReviewElementResponse> reviews
) {
}
