package reviewme.config.client;

import java.util.Objects;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import reviewme.config.client.dto.request.GithubAccessTokenRequest;
import reviewme.config.client.dto.response.GithubAccessTokenResponse;
import reviewme.config.client.dto.response.GithubUserInfoResponse;
import reviewme.config.client.exception.GithubOauthFailedException;

public class GithubOAuthClient {

    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String USER_INFO_URL = "https://api.github.com/user";

    private final RestClient restClient;
    private final String clientId;
    private final String clientSecret;

    public GithubOAuthClient(RestClient restClient, String clientId, String clientSecret) {
        this.restClient = restClient;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public GithubUserInfoResponse getUserInfo(String code) {
        String accessToken = requestAccessToken(code);
        return requestUserInfo(accessToken);
    }

    /**
     * https://docs.github.com/ko/rest/users/users
     */
    private GithubUserInfoResponse requestUserInfo(String accessToken) {
        return restClient.get()
                .uri(USER_INFO_URL)
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

    /**
     * https://docs.github.com/ko/apps/oauth-apps/building-oauth-apps/authorizing-oauth-apps
     */
    private String requestAccessToken(String code) {
        return restClient.post()
                .uri(ACCESS_TOKEN_URL)
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
}
