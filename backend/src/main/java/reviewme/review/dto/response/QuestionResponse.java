package reviewme.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 문항 응답")
public record QuestionResponse(

        @Schema(description = "리뷰 문항 ID")
        long id,

        @Schema(description = "리뷰 문항")
        String content
) {
}
