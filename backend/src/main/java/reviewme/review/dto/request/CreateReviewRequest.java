package reviewme.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "리뷰 작성 요청")
public record CreateReviewRequest(

        @Schema(description = "리뷰 요청 코드")
        @NotBlank(message = "리뷰 요청 코드가 필요합니다.")
        String reviewRequestCode,

        @Schema(description = "리뷰 답변 목록")
        @NotNull(message = "리뷰 답변이 없습니다.")
        List<CreateReviewContentRequest> reviewContents,

        @Schema(description = "선택 키워드 ID 목록")
        @NotNull(message = "선택된 키워드가 없습니다.")
        List<Long> keywords
) {
}
