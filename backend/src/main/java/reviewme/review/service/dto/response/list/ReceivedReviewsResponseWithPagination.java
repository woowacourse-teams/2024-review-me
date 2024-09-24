package reviewme.review.service.dto.response.list;

import java.util.List;

public record ReceivedReviewsResponseWithPagination(
        String revieweeName,
        String projectName,
        int totalSize,
        long lastReviewId,
        List<ReviewListElementResponse> reviews
) {
}
