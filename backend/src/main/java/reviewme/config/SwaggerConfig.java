package reviewme.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("리뷰미 API")
                        .description("이 문서는 리뷰미 API 구현 방법을 소개합니다.")
                        .version("1.0.0"));
    }
}
