package reviewme.reviewgroup.service.dto;

import jakarta.annotation.Nullable;

public record ReviewGroupSummaryResponse(

        @Nullable Long revieweeId,
        long reviewGroupId,
        String revieweeName,
        String projectName
) {
}
