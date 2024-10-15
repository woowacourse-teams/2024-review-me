package reviewme.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import reviewme.reviewgroup.controller.ReviewGroupSessionResolver;
import reviewme.reviewgroup.service.ReviewGroupService;
import reviewme.global.DuplicateRequestInterceptor;
import reviewme.global.HeaderPropertyArgumentResolver;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final RedisTemplate<String, Long> redisTemplate;
    private final ReviewGroupService reviewGroupService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ReviewGroupSessionResolver(reviewGroupService));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DuplicateRequestInterceptor(redisTemplate));
    }
}
