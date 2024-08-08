package reviewme.reviewgroup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "리뷰 그룹 생성 요청")
public record ReviewGroupCreationRequest(

        @Schema(description = "리뷰이 이름", maxLength = 50)
        @NotBlank(message = "리뷰이 이름은 공백일 수 없어요.")
        String revieweeName,

        @Schema(description = "프로젝트 이름", maxLength = 50)
        @NotBlank(message = "프로젝트 이름은 공백일 수 없어요.")
        String projectName
) {
}
