package reviewme.review.service.dto.response.detail;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

@Schema(name = "리뷰 상세 정보")
public record TemplateAnswerResponse(

        @Schema(description = "폼 ID")
        long formId,

        @Schema(description = "리뷰이 이름")
        String revieweeName,

        @Schema(description = "프로젝트 이름")
        String projectName,

        @Schema(description = "리뷰 작성일")
        LocalDate createdAt,

        @Schema(description = "섹션 목록")
        List<SectionAnswerResponse> sections
) {
}
