package reviewme.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "request-limit")
public record RequestLimitProperties(
        long maxFrequency,
        Duration duration,
        String host,
        int port
) {
}
