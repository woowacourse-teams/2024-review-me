package reviewme.review.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(name = "리뷰 생성 요청")
public record CreateReviewRequest(

        @Schema(description = "리뷰 요청 코드")
        @NotBlank
        String reviewRequestCode,

        @Schema(description = "답변 목록")
        @NotNull
        List<CreateReviewAnswerRequest> answers
) {
}
