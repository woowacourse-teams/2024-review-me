package reviewme.review.service.dto.response.detail;

import java.util.List;

public record TemplateAnswerResponse(
        long formId,
        String revieweeName,
        String projectName,
        List<SectionAnswerResponse> sections
) {
}
