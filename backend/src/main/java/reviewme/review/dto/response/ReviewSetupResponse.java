package reviewme.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "리뷰 작성 폼 응답")
public record ReviewSetupResponse(

        @Schema(description = "리뷰이 이름")
        String revieweeName,

        @Schema(description = "프로젝트명")
        String projectName,

        @Schema(description = "리뷰 문항 목록")
        List<QuestionSetupResponse> questions,

        @Schema(description = "키워드 목록")
        List<KeywordResponse> keywords
) {
}
