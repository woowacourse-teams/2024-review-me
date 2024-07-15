package reviewme.review;

import java.util.List;

public record CreateReviewRequest(
        Long reviewerId,
        Long reviewerGroupId,
        List<CreateReviewContentRequest> contents,
        List<Long> selectedKeywordIds) {
}
