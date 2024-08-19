package reviewme.reviewgroup.service.dto;

import jakarta.validation.constraints.NotBlank;

public record ReviewGroupCreationRequest(
        @NotBlank(message = "리뷰이 이름은 공백일 수 없어요.")
        String revieweeName,

        @NotBlank(message = "프로젝트 이름은 공백일 수 없어요.")
        String projectName
) {
}
