package reviewme.review.service.dto.response.list;

import java.time.LocalDate;
import java.util.List;

public record WrittenReviewElementResponse(
        long reviewId,
        String revieweeName,
        String projectName,
        LocalDate createdAt,
        String contentPreview,
        List<ReviewCategoryResponse> categories
) {
}
