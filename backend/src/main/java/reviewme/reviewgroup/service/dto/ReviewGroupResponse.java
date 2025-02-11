package reviewme.reviewgroup.service.dto;

import jakarta.annotation.Nullable;

public record ReviewGroupResponse(

        @Nullable Long revieweeId,
        String revieweeName,
        String projectName
) {
}
