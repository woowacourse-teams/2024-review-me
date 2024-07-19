package reviewme.review.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateReviewRequest(
        @NotNull(message = "리뷰어 아이디를 입력해주세요.")
        Long reviewerId,

        @NotNull(message = "리뷰어 그룹 아이디를 입력해주세요.")
        Long reviewerGroupId,

        @Valid
        @NotNull(message = "리뷰 내용을 입력해주세요.")
        List<CreateReviewContentRequest> contents,

        @NotNull(message = "키워드를 입력해주세요.")
        List<Long> selectedKeywordIds
) {
}
