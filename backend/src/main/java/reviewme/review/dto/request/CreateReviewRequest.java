package reviewme.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "리뷰 등록 요청")
public record CreateReviewRequest(
        @NotNull(message = "리뷰어 아이디를 입력해주세요.")
        @Schema(description = "리뷰어 ID")
        Long reviewerId,

        @NotNull(message = "리뷰어 그룹 아이디를 입력해주세요.")
        @Schema(description = "리뷰어 그룹 ID")
        Long reviewerGroupId,

        @Valid
        @NotNull(message = "리뷰 내용을 입력해주세요.")
        @Schema(description = "리뷰 내용 목록")
        List<CreateReviewContentRequest> contents,

        @NotNull(message = "키워드를 입력해주세요.")
        @Schema(description = "선택된 키워드 ID 목록")
        List<Long> selectedKeywordIds
) {
}
