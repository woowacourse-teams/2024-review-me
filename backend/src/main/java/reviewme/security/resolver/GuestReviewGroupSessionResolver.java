package reviewme.security.resolver;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import reviewme.security.resolver.dto.GuestReviewGroup;
import reviewme.security.resolver.exception.GuestReviewGroupSessionNotExistsException;
import reviewme.security.session.SessionManager;

@RequiredArgsConstructor
public class GuestReviewGroupSessionResolver implements HandlerMethodArgumentResolver {

    private final SessionManager sessionManager;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(GuestReviewGroupSession.class) &&
               parameter.getParameterType().equals(GuestReviewGroup.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        GuestReviewGroup guestReviewGroup = Optional.ofNullable(webRequest.getNativeRequest(HttpServletRequest.class))
                .map(sessionManager::getReviewRequestCode)
                .map(GuestReviewGroup::new)
                .orElse(null);

        GuestReviewGroupSession parameterAnnotation = parameter.getParameterAnnotation(GuestReviewGroupSession.class);
        if (parameterAnnotation.required() && guestReviewGroup == null) {
            throw new GuestReviewGroupSessionNotExistsException();
        }

        return guestReviewGroup;
    }
}
