package reviewme.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig {

    private static final Logger log = LoggerFactory.getLogger(CorsConfig.class);

    private CorsConfig() {
    }

    @Configuration
    @Profile("local")
    static class LocalCorsConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowCredentials(true);
        }
    }

    @Configuration
    @RequiredArgsConstructor
    @EnableConfigurationProperties(CorsProperties.class)
    @Profile({"dev", "prod"})
    static class ExposedCorsConfig implements WebMvcConfigurer {

        private final CorsProperties corsProperties;

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            log.info("Allowed origins: {}", corsProperties.allowedOrigins());
            registry.addMapping("/**")
                    .allowedOrigins(corsProperties.allowedOriginsAsArray())
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowCredentials(true);
        }
    }
}
