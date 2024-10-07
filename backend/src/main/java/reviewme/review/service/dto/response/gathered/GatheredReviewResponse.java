package reviewme.review.service.dto.response.gathered;

import jakarta.annotation.Nullable;
import java.util.List;

public record GatheredReviewResponse(
        QuestionResponse question,

        @Nullable
        List<AnswerContentResponse> answers,

        @Nullable
        List<VoteResponse> votes
) {
}
