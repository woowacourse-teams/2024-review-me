package reviewme.review.service.dto.response.detail;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "리뷰 섹션 정보")
public record SectionAnswerResponse(

        @Schema(description = "섹션 ID")
        long sectionId,

        @Schema(description = "말머리")
        String header,

        @Schema(description = "질문 목록")
        List<QuestionAnswerResponse> questions
) {
}
