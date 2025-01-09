package reviewme.template.service.dto.response;

import java.util.List;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.template.domain.Template;

public record TemplateResponse(
        long formId,
        String revieweeName,
        String projectName,
        List<SectionResponse> sections
) {

    public static TemplateResponse of(ReviewGroup reviewGroup, Template template) {
        List<SectionResponse> sectionResponses = template.getSections()
                .stream()
                .map(SectionResponse::from)
                .toList();

        return new TemplateResponse(
                reviewGroup.getTemplateId(),
                reviewGroup.getReviewee(),
                reviewGroup.getProjectName(),
                sectionResponses
        );
    }
}
