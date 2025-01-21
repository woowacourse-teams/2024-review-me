package reviewme.auth.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GitHubUserInfoResponse(
        @JsonProperty("login")
        String userName,

        @JsonProperty("email")
        String email
) {
}
