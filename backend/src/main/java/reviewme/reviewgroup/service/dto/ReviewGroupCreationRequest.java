package reviewme.reviewgroup.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record ReviewGroupCreationRequest(

        @NotEmpty(message = "리뷰이 이름을 입력해주세요.")
        String revieweeName,

        @NotEmpty(message = "프로젝트 이름을 입력해주세요.")
        String projectName,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        String groupAccessCode
) {
}
