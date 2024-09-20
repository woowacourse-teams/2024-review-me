package reviewme.review.service.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ReviewAuthRequest(
        @NotBlank(message = "리뷰 요청 코드를 입력하세요.")
        String reviewRequestCode,

        @NotBlank(message = "리뷰 확인 코드를 입력하세요.")
        String groupAccessCode) {
}
