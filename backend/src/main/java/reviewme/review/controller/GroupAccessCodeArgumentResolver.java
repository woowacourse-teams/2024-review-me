package reviewme.review.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import reviewme.review.controller.exception.MissingGroupAccessCodeException;

public class GroupAccessCodeArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String GROUP_ACCESS_CODE_HEADER = "GroupAccessCode";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ValidGroupAccessCode.class);
    }

    @Override
    public String resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String groupAccessCode = request.getHeader(GROUP_ACCESS_CODE_HEADER);

        if (groupAccessCode == null) {
            throw new MissingGroupAccessCodeException();
        }
        return groupAccessCode;
    }
}
