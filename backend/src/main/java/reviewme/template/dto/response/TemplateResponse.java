package reviewme.template.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "리뷰 폼 응답")
public record TemplateResponse(

        @Schema(description = "폼 ID")
        long formId,

        @Schema(description = "리뷰이 이름")
        String revieweeName,

        @Schema(description = "프로젝트 이름")
        String projectName,

        @Schema(description = "섹션 목록")
        List<SectionResponse> sections
) {
}
