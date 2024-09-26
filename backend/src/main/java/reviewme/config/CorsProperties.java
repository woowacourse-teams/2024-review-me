package reviewme.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cors")
public record CorsProperties(List<String> allowedOrigins) {

    public String[] allowedOriginsAsArray() {
        return allowedOrigins.toArray(new String[0]);
    }
}
