package reviewme.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰어 그룹 응답")
public record ReviewDetailReviewerGroupResponse(

        @Schema(description = "리뷰어 그룹 아이디")
        long id,

        @Schema(description = "리뷰 그룹 이름 (레포지토리명)")
        String name,

        @Schema(description = "그룹 소개")
        String description,

        @Schema(description = "썸네일 URL")
        String thumbnailUrl
) {
}
