package reviewme.review.service.dto.response.detail;

import java.time.LocalDate;
import java.util.List;

public record TemplateAnswerResponse(
        long formId,
        String revieweeName,
        String projectName,
        LocalDate createdAt,
        List<SectionAnswerResponse> sections
) {
}
