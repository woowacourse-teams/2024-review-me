package reviewme.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import reviewme.auth.controller.GuestReviewGroupSessionResolver;
import reviewme.auth.controller.LoginMemberSessionResolver;
import reviewme.global.session.SessionManager;
import reviewme.reviewgroup.controller.ReviewGroupSessionResolver;
import reviewme.reviewgroup.service.ReviewGroupService;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final ReviewGroupService reviewGroupService;
    private final SessionManager sessionManager;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ReviewGroupSessionResolver(reviewGroupService));
        resolvers.add(new LoginMemberSessionResolver(sessionManager));
        resolvers.add(new GuestReviewGroupSessionResolver(sessionManager));
    }
}
