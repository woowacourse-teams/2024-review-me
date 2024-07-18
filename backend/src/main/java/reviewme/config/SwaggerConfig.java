package reviewme.config;

import io.swagger.v3.oas.models.OpenAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reviewme.config.properties.SwaggerProperties;

@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@RequiredArgsConstructor
public class SwaggerConfig {

    private final SwaggerProperties swaggerProperties;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(swaggerProperties.swaggerInfo());
    }
}
