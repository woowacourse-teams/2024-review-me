package reviewme.review.service.dto.response.gathered;

import java.util.List;

public record ReviewsGatherBySectionResponse(
        List<ReviewsGatherByQuestionResponse> reviews
) {
}
