package reviewme.global;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import reviewme.global.exception.MissingHeaderPropertyException;

public class HeaderPropertyArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(HeaderProperty.class);
    }

    @Override
    public String resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        HeaderProperty parameterAnnotation = parameter.getParameterAnnotation(HeaderProperty.class);
        String headerName = parameterAnnotation.headerName();
        String headerProperty = request.getHeader(headerName);

        if (headerProperty == null) {
            throw new MissingHeaderPropertyException(headerName);
        }
        return headerProperty;
    }
}
