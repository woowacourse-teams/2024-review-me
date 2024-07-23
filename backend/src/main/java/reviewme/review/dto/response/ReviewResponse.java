package reviewme.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import reviewme.keyword.dto.response.KeywordResponse;
import reviewme.member.dto.response.ReviewerGroupResponse;

@Schema(description = "리뷰 응답")
public record ReviewResponse(

        @Schema(description = "리뷰 ID")
        long id,

        @Schema(description = "리뷰 생성 시각")
        LocalDateTime createdAt,

        @Schema(description = "리뷰어 그룹")
        ReviewerGroupResponse reviewerGroup,

        @Schema(description = "리뷰 내용 목록")
        List<ReviewContentResponse> contents,

        @Schema(description = "선택된 키워드 목록")
        List<KeywordResponse> keywords
) {
}
