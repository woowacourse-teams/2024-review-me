package reviewme.config.client;

import java.util.Objects;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import reviewme.config.client.dto.request.GithubAccessTokenRequest;
import reviewme.config.client.dto.response.GithubAccessTokenResponse;
import reviewme.config.client.dto.response.GithubUserInfoResponse;
import reviewme.config.client.exception.GithubOauthFailedException;

@EnableConfigurationProperties(GithubOauthProperties.class)
public class GithubOAuthClient {

    private final RestClient restClient;
    private final String clientId;
    private final String clientSecret;
    private final String accessTokenUri;
    private final String userInfoUri;

    public GithubOAuthClient(RestClient restClient, String clientId, String clientSecret,
                             String accessTokenUri, String userInfoUri) {
        this.restClient = restClient;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUri = accessTokenUri;
        this.userInfoUri = userInfoUri;
    }

    public GithubUserInfoResponse getUserInfo(String code) {
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
                .body(new GithubAccessTokenRequest(clientId, clientSecret, code))
                .exchange((request, response) -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        return Objects.requireNonNull(response.bodyTo(GithubAccessTokenResponse.class));
                    } else {
                        throw new GithubOauthFailedException();
                    }
                }).accessToken();
    }

    /**
     * https://docs.github.com/ko/rest/users/users
     */
    private GithubUserInfoResponse requestUserInfo(String accessToken) {
        return restClient.get()
                .uri(userInfoUri)
                .header("Authorization", "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        return Objects.requireNonNull(response.bodyTo(GithubUserInfoResponse.class));
                    } else {
                        throw new GithubOauthFailedException();
                    }
                });
    }
}
