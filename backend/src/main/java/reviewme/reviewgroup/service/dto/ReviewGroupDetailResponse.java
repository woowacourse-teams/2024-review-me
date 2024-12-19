package reviewme.reviewgroup.service.dto;

import java.time.LocalDate;

public record ReviewGroupDetailResponse(
        String revieweeName,
        String projectName,
        LocalDate createdAt,
        String reviewRequestCode
) {
}
