package reviewme.reviewgroup.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CheckValidAccessRequest(

        @NotBlank(message = "리뷰 요청 코드를 입력하세요.")
        String reviewRequestCode,

        @NotEmpty(message = "리뷰 확인 코드를 입력하세요.")
        String groupAccessCode
) {
}
