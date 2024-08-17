package reviewme.review.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Schema(name = "리뷰 생성 요청")
public record CreateReviewRequest(

        @Schema(description = "리뷰 요청 코드")
        @NotBlank(message = "리뷰 요청 코드를 입력해주세요.")
        String reviewRequestCode,

        @Schema(description = "답변 목록")
        @NotEmpty(message = "답변 내용을 입력해주세요.")
        List<CreateReviewAnswerRequest> answers
) {
}
