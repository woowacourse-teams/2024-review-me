package reviewme.review.service.dto.response.list;

import java.time.LocalDateTime;
import java.util.List;

public record AuthoredReviewElementResponse(

        long reviewId,
        String revieweeName,
        String projectName,
        LocalDateTime createdAt,
        String contentPreview,
        List<SelectedCategoryOptionResponse> categoryOptions
) {
}
