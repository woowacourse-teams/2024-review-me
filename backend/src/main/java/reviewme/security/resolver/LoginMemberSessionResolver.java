package reviewme.security.resolver;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import reviewme.security.resolver.dto.LoginMember;
import reviewme.security.resolver.exception.LoginMemberSessionNotExistsException;
import reviewme.security.session.SessionManager;

@RequiredArgsConstructor
public class LoginMemberSessionResolver implements HandlerMethodArgumentResolver {

    private final SessionManager sessionManager;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMemberSession.class) &&
               parameter.getParameterType().equals(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        LoginMember loginMember = Optional.ofNullable(webRequest.getNativeRequest(HttpServletRequest.class))
                .map(sessionManager::getGitHubMember)
                .map(gitHubMember -> new LoginMember(gitHubMember.getMemberId()))
                .orElse(null);

        LoginMemberSession parameterAnnotation = parameter.getParameterAnnotation(LoginMemberSession.class);
        if (parameterAnnotation.required() && loginMember == null) {
            throw new LoginMemberSessionNotExistsException();
        }

        return loginMember;
    }
}
