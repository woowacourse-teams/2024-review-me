package reviewme.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 내용 응답")
public record ReviewContentResponse(

        @Schema(description = "리뷰 내용 ID")
        long id,

        @Schema(description = "리뷰 문항")
        String question,

        @Schema(description = "리뷰 문항에 대한 답변")
        String answer
) {
}
