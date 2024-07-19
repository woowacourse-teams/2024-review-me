package reviewme.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 응답")
public record MemberResponse(

        @Schema(description = "사용자 ID")
        long id,

        @Schema(description = "사용자 이름")
        String name
) {
}
