package reviewme.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "리뷰 문항 응답")
public record QuestionSetupResponse(

        @Schema(description = "리뷰 문항 ID")
        long id,

        @Schema(description = "리뷰 문항")
        String content
) {
}
