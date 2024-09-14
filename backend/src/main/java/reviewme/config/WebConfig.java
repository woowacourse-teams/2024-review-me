package reviewme.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import reviewme.global.HeaderPropertyArgumentResolver;
import reviewme.review.controller.ReviewRequestCodeArgumentResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new HeaderPropertyArgumentResolver());
        resolvers.add(new ReviewRequestCodeArgumentResolver());
    }
}
