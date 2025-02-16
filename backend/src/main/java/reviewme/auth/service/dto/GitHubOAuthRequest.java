package reviewme.auth.service.dto;

import jakarta.validation.constraints.NotBlank;

public record GitHubOAuthRequest(
        @NotBlank(message = "깃허브 임시 코드를 입력해주세요.")
        String code) {
}
