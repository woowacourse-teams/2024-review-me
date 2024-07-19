package reviewme.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "리뷰어 그룹 응답")
public record ReviewerGroupResponse(

        @Schema(description = "리뷰어 그룹 아이디")
        long id,

        @Schema(description = "리뷰 그룹 이름 (레포지토리명)")
        String name,

        @Schema(description = "리뷰 작성 기한")
        LocalDateTime deadline,

        @Schema(description = "리뷰이")
        MemberResponse reviewee
) {
}
