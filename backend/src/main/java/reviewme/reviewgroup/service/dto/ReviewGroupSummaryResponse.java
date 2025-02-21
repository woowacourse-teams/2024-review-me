package reviewme.reviewgroup.service.dto;

import jakarta.annotation.Nullable;

public record ReviewGroupSummaryResponse(

        @Nullable Long revieweeId,
        String revieweeName,
        String projectName
) {
}
