package reviewme.config.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "github.oauth")
public record GithubOauthProperties(
        String clientId,
        String clientSecret,
        String accessTokenUri,
        String userInfoUri
) {
}
