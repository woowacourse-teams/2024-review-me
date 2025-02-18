package reviewme.review.service.dto.response.list;

import java.time.LocalDateTime;
import java.util.List;

public record ReceivedReviewPageElementResponse(

        long reviewId,
        LocalDateTime createdAt,
        String contentPreview,
        List<SelectedCategoryOptionResponse> categoryOptions
) {
}
