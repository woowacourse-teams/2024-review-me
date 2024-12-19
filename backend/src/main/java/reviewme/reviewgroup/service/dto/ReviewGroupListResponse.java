package reviewme.reviewgroup.service.dto;

import java.util.List;

public record ReviewGroupListResponse(
        boolean isLastPage,
        List<ReviewGroupDetailResponse> reviewGroups
) {
}
