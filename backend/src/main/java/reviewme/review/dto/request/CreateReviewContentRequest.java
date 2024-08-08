package reviewme.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "리뷰 내용 등록 요청")
public record CreateReviewContentRequest(

        @Schema(description = "리뷰 문항")
        @NotNull(message = "질문을 입력해주세요.")
        Long questionId,

        @Schema(description = "리뷰 문항에 대한 답변", minLength = 20, maxLength = 1_000)
        @NotBlank(message = "답변을 입력해주세요.")
        String answer
) {
}
