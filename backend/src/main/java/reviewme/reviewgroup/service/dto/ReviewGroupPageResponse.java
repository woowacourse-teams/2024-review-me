package reviewme.reviewgroup.service.dto;

import java.util.List;

public record ReviewGroupPageResponse(
        long lastReviewGroupId,
        boolean isLastPage,
        List<ReviewGroupPageElementResponse> reviewGroups
) {
}
