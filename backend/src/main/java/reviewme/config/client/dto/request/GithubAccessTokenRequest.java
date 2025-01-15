package reviewme.config.client.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubAccessTokenRequest(
        @JsonProperty("client_id")
        String clientId,

        @JsonProperty("client_secret")
        String clientSecret,

        @JsonProperty("code")
        String code
) {
}
