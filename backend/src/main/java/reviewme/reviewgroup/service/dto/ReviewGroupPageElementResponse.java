package reviewme.reviewgroup.service.dto;

import java.time.LocalDateTime;

public record ReviewGroupPageElementResponse(

        long reviewGroupId,
        String revieweeName,
        String projectName,
        String reviewRequestCode,
        LocalDateTime createdAt,
        long reviewCount
) {
}
