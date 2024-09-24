package reviewme.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // TODO: Origin 서버 도메인으로 설정
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedMethods("GET", "POST")
                .allowedOriginPatterns("*");
    }
}
