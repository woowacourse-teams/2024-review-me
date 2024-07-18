package reviewme.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "리뷰 내용 등록 요청")
public record CreateReviewContentRequest(
        @NotNull(message = "리뷰 항목 순서를 입력해주세요.")
        @Schema(description = "리뷰 항목 순서")
        Long order,

        @NotNull(message = "질문을 입력해주세요.")
        @Schema(description = "리뷰 문항")
        String question,

        @NotNull(message = "답변을 입력해주세요.")
        @Schema(description = "리뷰 문항에 대한 답변")
        String answer
) {
}
