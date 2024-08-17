package reviewme.review.service.dto.request;

import java.util.List;

public record CreateReviewAnswerRequest(
        long questionId,
        List<Long> selectedOptionIds,
        String text
) {
}
