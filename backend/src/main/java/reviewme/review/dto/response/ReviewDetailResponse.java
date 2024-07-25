package reviewme.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "리뷰 응답")
public record ReviewDetailResponse(

        @Schema(description = "리뷰 ID")
        long id,

        @Schema(description = "리뷰 생성 시각")
        LocalDate createdAt,

        @Schema(description = "공개 여부")
        boolean isPublic,

        @Schema(description = "리뷰어 그룹")
        ReviewDetailReviewerGroupResponse reviewerGroup,

        @Schema(description = "리뷰 내용 목록")
        List<ReviewDetailReviewContentResponse> reviews,

        @Schema(description = "선택된 키워드 목록")
        List<String> keywords
) {
}
