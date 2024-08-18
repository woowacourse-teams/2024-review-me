package reviewme.review.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(name = "리뷰 답변 내용")
public record CreateReviewAnswerRequest(

        @Schema(description = "질문 ID")
        @NotNull
        long questionId,

        @Schema(description = "선택된 옵션 ID 목록", nullable = true)
        List<Long> selectedOptionIds,

        @Schema(description = "답변 내용", nullable = true)
        String text
) {
}
