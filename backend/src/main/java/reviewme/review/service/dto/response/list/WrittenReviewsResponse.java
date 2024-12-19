package reviewme.review.service.dto.response.list;

import java.util.List;

public record WrittenReviewsResponse(
        long memberId,
        List<WrittenReviewElementResponse> reviews,
        long lastReviewId,
        boolean isLastPage
) {
}
