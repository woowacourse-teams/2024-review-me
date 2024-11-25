package reviewme.config.requestlimit;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class RequestLimitWebConfig implements WebMvcConfigurer {

    private final RedisTemplate<String, Long> redisTemplate;
    private final RequestLimitProperties requestLimitProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestLimitInterceptor(redisTemplate, requestLimitProperties));
    }
}
