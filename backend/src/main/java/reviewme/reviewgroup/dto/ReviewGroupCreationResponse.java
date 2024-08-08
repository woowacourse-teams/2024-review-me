package reviewme.reviewgroup.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "리뷰 그룹 생성 완료 응답")
public record ReviewGroupCreationResponse(

        @Schema(description = "리뷰 요청 코드")
        String reviewRequestCode,

        @Schema(description = "그룹 접근 코드")
        String groupAccessCode
) {
}
