package reviewme.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import reviewme.keyword.dto.response.KeywordResponse;
import reviewme.member.dto.response.MemberResponse;
import reviewme.member.dto.response.ReviewerGroupResponse;

@Schema(description = "리뷰 응답")
public record ReviewResponse(

        @Schema(description = "리뷰 ID")
        long id,

//        LocalDateTime createdAt,

        @Schema(description = "리뷰어")
        MemberResponse reviewer,

        @Schema(description = "리뷰어 그룹")
        ReviewerGroupResponse reviewerGroup,

        @Schema(description = "리뷰 내용 목록")
        List<ReviewContentResponse> contents,

        @Schema(description = "선택된 키워드 목록")
        List<KeywordResponse> keywords
) {
}
