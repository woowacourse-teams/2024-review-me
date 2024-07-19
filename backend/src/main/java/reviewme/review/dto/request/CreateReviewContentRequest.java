package reviewme.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 내용 등록 요청")
public record CreateReviewContentRequest(

        @Schema(description = "리뷰 항목 순서")
        Long order,

        @Schema(description = "리뷰 문항")
        String question,

        @Schema(description = "리뷰 문항에 대한 답변")
        String answer) {
}
