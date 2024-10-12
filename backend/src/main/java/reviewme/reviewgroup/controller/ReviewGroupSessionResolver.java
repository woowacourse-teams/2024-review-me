package reviewme.reviewgroup.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import reviewme.review.service.exception.ReviewGroupNotFoundByReviewRequestCodeException;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;

@Component
@RequiredArgsConstructor
public class ReviewGroupSessionResolver implements HandlerMethodArgumentResolver {

    private static final String SESSION_KEY = "reviewRequestCode";

    private final ReviewGroupRepository reviewGroupRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ReviewGroupSession.class);
    }

    @Override
    public ReviewGroup resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                       NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpSession session = request.getSession(false);

        // 세션이 없거나, 세션 안에 reviewRequestCode가 존재하지 않는 경우
        if (session == null) {
            throw new ReviewGroupSessionNotFoundException();
        }
        String reviewRequestCode = (String) session.getAttribute(SESSION_KEY);
        if (reviewRequestCode == null) {
            throw new ReviewGroupSessionNotFoundException();
        }
        return reviewGroupRepository.findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(() -> new ReviewGroupNotFoundByReviewRequestCodeException(reviewRequestCode));
    }
}
