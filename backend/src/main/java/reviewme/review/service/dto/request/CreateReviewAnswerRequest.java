package reviewme.review.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(name = "리뷰 답변 내용")
public record CreateReviewAnswerRequest(

        @Schema(description = "질문 ID")
        @NotNull(message = "질문 ID를 입력해주세요.")
        Long questionId,

        @Schema(description = "선택된 옵션 ID 목록", nullable = true)
        @Nullable
        List<Long> selectedOptionIds,

        @Schema(description = "답변 내용", nullable = true)
        @Nullable
        String text
) {
}
