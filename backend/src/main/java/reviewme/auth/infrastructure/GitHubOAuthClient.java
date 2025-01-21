package reviewme.auth.infrastructure;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestHeadersSpec.ExchangeFunction;
import reviewme.auth.infrastructure.dto.request.GitHubAccessTokenRequest;
import reviewme.auth.infrastructure.dto.response.GitHubAccessTokenResponse;
import reviewme.auth.infrastructure.dto.response.GitHubUserInfoResponse;
import reviewme.auth.infrastructure.exception.GitHubOAuthFailedException;
import reviewme.config.client.GitHubOAuthProperties;

@Component
@RequiredArgsConstructor
public class GitHubOAuthClient {

    private final RestClient restClient;
    private final GitHubOAuthProperties properties;

    public GitHubUserInfoResponse getUserInfo(String code) {
        String accessToken = requestAccessToken(code);
        return requestUserInfo(accessToken);
    }

    /**
     * https://docs.github.com/ko/apps/oauth-apps/building-oauth-apps/authorizing-oauth-apps
     */
    private String requestAccessToken(String code) {
        return restClient.post()
                .uri(properties.accessTokenUri())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(new GitHubAccessTokenRequest(properties.clientId(), properties.clientSecret(), code))
                .exchange(handleResponse(GitHubAccessTokenResponse.class))
                .accessToken();
    }

    /**
     * https://docs.github.com/ko/rest/users/users
     */
    private GitHubUserInfoResponse requestUserInfo(String accessToken) {
        return restClient.get()
                .uri(properties.userInfoUri())
                .header("Authorization", "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .exchange(handleResponse(GitHubUserInfoResponse.class));
    }

    private static <T> ExchangeFunction<T> handleResponse(Class<T> t) {
        return (request, response) -> {
            if (response.getStatusCode().is2xxSuccessful()) {
                try {
                    return Objects.requireNonNull(response.bodyTo(t));
                } catch (Exception e) {
                    throw new GitHubOAuthFailedException(response.getStatusCode(), "Failed to parse response body");
                }
            } else {
                throw new GitHubOAuthFailedException(response.getStatusCode(), response.bodyTo(String.class));
            }
        };
    }
}
