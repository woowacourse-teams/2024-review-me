package reviewme.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReceivedReviewReviewerGroupResponse(

        @Schema(description = "리뷰어 그룹 아이디")
        long id,

        @Schema(description = "리뷰어 그룹 이름")
        String name,

        @Schema(description = "리뷰어 그룹 썸네일 이미지 URL")
        String thumbnailUrl
) {
}
