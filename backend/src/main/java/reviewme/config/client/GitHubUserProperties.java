package reviewme.config.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "github.user")
public record GitHubUserProperties(
        String profileUrlFormat
) {
}
