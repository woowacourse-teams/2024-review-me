package reviewme.reviewgroup.service.dto;

import jakarta.validation.constraints.NotBlank;

public record ReviewGroupCreationRequest(

        @NotBlank(message = "리뷰이 이름을 입력해주세요.")
        String revieweeName,

        @NotBlank(message = "프로젝트 이름을 입력해주세요.")
        String projectName,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        String groupAccessCode
) {
}
