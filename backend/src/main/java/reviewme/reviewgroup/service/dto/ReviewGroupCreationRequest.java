package reviewme.reviewgroup.service.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;

public record ReviewGroupCreationRequest(

        @NotEmpty(message = "리뷰이 이름을 입력해주세요.")
        String revieweeName,

        @NotEmpty(message = "프로젝트 이름을 입력해주세요.")
        String projectName,

        @Nullable
        String groupAccessCode
) {
}
