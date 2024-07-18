package reviewme.review.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateReviewContentRequest(
        @NotNull(message = "리뷰 항목 순서를 입력해주세요.")
        Long order,

        @NotNull(message = "질문을 입력해주세요.")
        String question,

        @NotNull(message = "답변을 입력해주세요.")
        String answer
) {
}
