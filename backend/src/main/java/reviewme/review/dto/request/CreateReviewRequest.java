package reviewme.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "리뷰 등록 요청")
public record CreateReviewRequest(

        @Schema(description = "리뷰어 ID")
        Long reviewerId,

        @Schema(description = "리뷰어 그룹 ID")
        Long reviewerGroupId,

        @Schema(description = "리뷰 내용 목록")
        List<CreateReviewContentRequest> contents,

        @Schema(description = "선택된 키워드 ID 목록")
        List<Long> selectedKeywordIds) {
}
