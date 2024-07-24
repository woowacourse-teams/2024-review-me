package reviewme.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "리뷰 생성 시 필요한 리뷰어 그룹 응답")
public record ReviewCreationReviewerGroupResponse(

        @Schema(description = "리뷰어 그룹 아이디")
        long id,

        @Schema(description = "리뷰 그룹 이름 (레포지토리명)")
        String name,

        @Schema(description = "그룹 소개")
        String description,

        @Schema(description = "리뷰 작성 기한")
        LocalDate deadline,

        @Schema(description = "썸네일 URL")
        String thumbnailUrl,

        @Schema(description = "리뷰이")
        MemberResponse reviewee
) {
}
