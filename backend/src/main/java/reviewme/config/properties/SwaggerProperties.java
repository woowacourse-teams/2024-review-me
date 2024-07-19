package reviewme.config.properties;

import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "docs.info")
public record SwaggerProperties(
        String title,
        String description,
        String version
) {

    public Info swaggerInfo() {
        return new Info()
                .title(title)
                .description(description)
                .version(version);
    }
}
