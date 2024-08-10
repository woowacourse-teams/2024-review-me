package reviewme.template.dto.response;

import java.util.List;

public record TemplateResponse(

        long formId,
        String revieweeName,
        String projectName,
        List<SectionResponse> sections
) {
}
