package reviewme.config.client;

import java.util.Objects;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestHeadersSpec.ExchangeFunction;
import reviewme.config.client.dto.request.GitHubAccessTokenRequest;
import reviewme.config.client.dto.response.GitHubAccessTokenResponse;
import reviewme.config.client.dto.response.GitHubUserInfoResponse;
import reviewme.config.client.exception.GitHubOAuthFailedException;

public class GitHubOAuthClient {

    private final RestClient restClient;
    private final String clientId;
    private final String clientSecret;
    private final String accessTokenUri;
    private final String userInfoUri;

    public GitHubOAuthClient(RestClient restClient, GitHubOAuthProperties properties) {
        this.restClient = restClient;
        this.clientId = properties.clientId();
        this.clientSecret = properties.clientSecret();
        this.accessTokenUri = properties.accessTokenUri();
        this.userInfoUri = properties.userInfoUri();
    }

    public GitHubUserInfoResponse getUserInfo(String code) {
        String accessToken = requestAccessToken(code);
        return requestUserInfo(accessToken);
    }

    /**
     * https://docs.github.com/ko/apps/oauth-apps/building-oauth-apps/authorizing-oauth-apps
     */
    private String requestAccessToken(String code) {
        return restClient.post()
                .uri(accessTokenUri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(new GitHubAccessTokenRequest(clientId, clientSecret, code))
                .exchange(handleResponse(GitHubAccessTokenResponse.class))
                .accessToken();
    }

    /**
     * https://docs.github.com/ko/rest/users/users
     */
    private GitHubUserInfoResponse requestUserInfo(String accessToken) {
        return restClient.get()
                .uri(userInfoUri)
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
