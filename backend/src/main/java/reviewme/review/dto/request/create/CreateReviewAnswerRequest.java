package reviewme.review.dto.request.create;

import java.util.List;

public record CreateReviewAnswerRequest(
        long questionId,
        List<Long> selectedOptionIds,
        String text
) {
}
