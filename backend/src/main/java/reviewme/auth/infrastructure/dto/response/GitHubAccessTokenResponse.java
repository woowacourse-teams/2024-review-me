package reviewme.auth.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GitHubAccessTokenResponse(
        @JsonProperty("access_token") String accessToken
) {
}
