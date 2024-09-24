package reviewme.review.service.dto.response.list;

import java.util.List;

public record ReceivedReviewsResponse3(
        String revieweeName,
        String projectName,
        int totalSize,
        long lastReviewId,
        List<ReviewListElementResponse> reviews
) {
}
