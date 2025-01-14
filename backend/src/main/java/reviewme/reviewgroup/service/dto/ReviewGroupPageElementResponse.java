package reviewme.reviewgroup.service.dto;

import java.time.LocalDate;

public record ReviewGroupPageElementResponse(
        String revieweeName,
        String projectName,
        LocalDate createdAt,
        String reviewRequestCode,
        int reviewCount
) {
}
