package reviewme.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import reviewme.keyword.dto.response.KeywordResponse;
import reviewme.member.dto.response.ReviewCreationReviewerGroupResponse;
import reviewme.review.dto.response.QuestionResponse;

@Schema(description = "리뷰 생성 시 필요한 정보 응답")
public record ReviewCreationResponse(

        @Schema(description = "리뷰어 그룹")
        ReviewCreationReviewerGroupResponse reviewerGroup,

        @Schema(description = "리뷰 문항 목록")
        List<QuestionResponse> questions,

        @Schema(description = "키워드 목록")
        List<KeywordResponse> keywords
) {
}
