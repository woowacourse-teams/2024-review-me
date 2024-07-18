package reviewme.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateReviewContentRequest(
        @NotNull(message = "리뷰 항목 순서를 입력해주세요.")
        Long order,

        @NotBlank(message = "질문을 입력해주세요.")
        String question,

        @NotBlank(message = "답변을 입력해주세요.")
        String answer
) {
}
