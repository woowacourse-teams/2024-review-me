package reviewme.review.service.dto.request;

import java.util.List;

public record CreateReviewRequest(
        String reviewRequestCode,
        List<CreateReviewAnswerRequest> answers
) {
}
