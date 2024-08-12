package reviewme.review.dto.request.create;

import java.util.List;

public record CreateReviewRequest2(
        String reviewRequestCode,
        List<CreateReviewAnswerRequest> answers
) {
}
