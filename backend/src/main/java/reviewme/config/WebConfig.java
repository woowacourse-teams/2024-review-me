package reviewme.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import reviewme.security.resolver.GuestReviewGroupSessionResolver;
import reviewme.security.resolver.LoginMemberSessionResolver;
import reviewme.security.session.SessionManager;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final SessionManager sessionManager;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberSessionResolver(sessionManager));
        resolvers.add(new GuestReviewGroupSessionResolver(sessionManager));
    }
}
