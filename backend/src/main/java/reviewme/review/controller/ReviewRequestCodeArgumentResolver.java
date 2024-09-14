package reviewme.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class ReviewRequestCodeArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ReviewRequestCode.class) &&
               parameter.getParameterType().equals(String.class);
    }

    @Override
    public String resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        // TODO: Extrace review request code from request, jwt token
        // 이곳에서 리뷰 요청 토큰을 검증할까요, 서비스에서 검증할까요?
        // 이곳에서 토큰을 검증하려면 Repository 의존성이 필요합니다
        // WebConfig가 Repository를 알게 됩니다
        return "";
    }
}
