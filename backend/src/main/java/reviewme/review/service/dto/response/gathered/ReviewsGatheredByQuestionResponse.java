package reviewme.review.service.dto.response.gathered;

import jakarta.annotation.Nullable;
import java.util.List;

public record ReviewsGatheredByQuestionResponse(
        SimpleQuestionResponse question,

        @Nullable
        List<TextResponse> answers,

        @Nullable
        List<VoteResponse> votes
) {
}
