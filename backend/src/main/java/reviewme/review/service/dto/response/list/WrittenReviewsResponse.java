package reviewme.review.service.dto.response.list;

import java.util.List;

public record WrittenReviewsResponse(
        List<WrittenReviewElementResponse> reviews,
        long lastReviewId,
        boolean isLastPage
) {
}
