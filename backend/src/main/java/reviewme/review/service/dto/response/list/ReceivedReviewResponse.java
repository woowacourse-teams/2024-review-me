package reviewme.review.service.dto.response.list;

import java.time.LocalDate;
import java.util.List;

public record ReceivedReviewResponse(
        long reviewId,
        LocalDate createdAt,
        String contentPreview,
        List<ReceivedReviewCategoryResponse> categories
) {
}
