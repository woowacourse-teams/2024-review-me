package reviewme.review.service.dto.response.detail;

import java.time.LocalDate;
import java.util.List;

public record ReviewDetailResponse(
        long formId,
        LocalDate createdAt,
        List<SectionAnswerResponse> sections
) {
}
