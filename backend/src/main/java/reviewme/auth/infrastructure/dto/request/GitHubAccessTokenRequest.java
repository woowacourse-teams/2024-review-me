package reviewme.auth.infrastructure.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubAccessTokenRequest(
        @JsonProperty("client_id")
        String clientId,

        @JsonProperty("client_secret")
        String clientSecret,

        @JsonProperty("code")
        String code
) {
}
