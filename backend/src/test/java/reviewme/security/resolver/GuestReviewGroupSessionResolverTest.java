package reviewme.security.resolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import reviewme.security.resolver.dto.GuestReviewGroup;
import reviewme.security.resolver.exception.GuestReviewGroupSessionNotExistsException;
import reviewme.security.session.SessionManager;

@ExtendWith(MockitoExtension.class)
class GuestReviewGroupSessionResolverTest {

    @InjectMocks
    private GuestReviewGroupSessionResolver guestReviewGroupSessionResolver;

    @Mock
    private MethodParameter methodParameter;

    @Mock
    private NativeWebRequest nativeWebRequest;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpSession httpSession;

    @Mock
    private SessionManager sessionManager;

    @Test
    void 세션을_통해_비회원_리뷰_그룹_코드를_반환한다() {
        // given
        String reviewRequestCode = "reviewRequestCode";

        given(nativeWebRequest.getNativeRequest(HttpServletRequest.class)).willReturn(httpServletRequest);
        given(httpServletRequest.getSession()).willReturn(httpSession);
        given(sessionManager.getReviewRequestCode(httpSession)).willReturn(reviewRequestCode);

        GuestReviewGroupSession annotation = mock(GuestReviewGroupSession.class);
        given(annotation.required()).willReturn(false);
        given(methodParameter.getParameterAnnotation(GuestReviewGroupSession.class)).willReturn(annotation);

        // when
        GuestReviewGroup actual = (GuestReviewGroup) guestReviewGroupSessionResolver.resolveArgument(
                methodParameter, null, nativeWebRequest, null);

        // then
        assertThat(actual.reviewRequestCode()).isEqualTo(reviewRequestCode);
    }

    @Test
    void 어노테이션의_속성이_flase일때_세션이_없다면_null을_반환한다() {
        // given
        given(nativeWebRequest.getNativeRequest(HttpServletRequest.class)).willReturn(httpServletRequest);
        given(httpServletRequest.getSession()).willReturn(null);

        GuestReviewGroupSession annotation = mock(GuestReviewGroupSession.class);
        given(annotation.required()).willReturn(false);
        given(methodParameter.getParameterAnnotation(GuestReviewGroupSession.class)).willReturn(annotation);

        // when
        GuestReviewGroup actual = (GuestReviewGroup) guestReviewGroupSessionResolver.resolveArgument(
                methodParameter, null, nativeWebRequest, null);

        // then
        assertThat(actual).isNull();
    }

    @Test
    void 어노테이션의_속성이_false일때_세션에_저장된_데이터가_없다면_null을_반환한다() {
        // given
        given(nativeWebRequest.getNativeRequest(HttpServletRequest.class)).willReturn(httpServletRequest);
        given(httpServletRequest.getSession()).willReturn(httpSession);
        given(sessionManager.getReviewRequestCode(httpSession)).willReturn(null);

        GuestReviewGroupSession annotation = mock(GuestReviewGroupSession.class);
        given(annotation.required()).willReturn(false);
        given(methodParameter.getParameterAnnotation(GuestReviewGroupSession.class)).willReturn(annotation);

        // when
        GuestReviewGroup actual = (GuestReviewGroup) guestReviewGroupSessionResolver.resolveArgument(
                methodParameter, null, nativeWebRequest, null);

        // then
        assertThat(actual).isNull();
    }

    @Test
    void 어노테이션의_속성이_true일때_세션이_없다면_예외가_발생한다() {
        // given
        given(nativeWebRequest.getNativeRequest(HttpServletRequest.class)).willReturn(httpServletRequest);
        given(httpServletRequest.getSession()).willReturn(null);

        GuestReviewGroupSession annotation = mock(GuestReviewGroupSession.class);
        given(annotation.required()).willReturn(true);
        given(methodParameter.getParameterAnnotation(GuestReviewGroupSession.class)).willReturn(annotation);

        // when, then
        assertThatThrownBy(() -> guestReviewGroupSessionResolver.resolveArgument(
                methodParameter, null, nativeWebRequest, null))
                .isInstanceOf(GuestReviewGroupSessionNotExistsException.class);
    }

    @Test
    void 어노테이션의_속성이_true일때_세션에_저장된_데이터가_없다면_예외가_발생한다() {
        // given
        given(nativeWebRequest.getNativeRequest(HttpServletRequest.class)).willReturn(httpServletRequest);
        given(httpServletRequest.getSession()).willReturn(httpSession);
        given(sessionManager.getReviewRequestCode(httpSession)).willReturn(null);

        GuestReviewGroupSession annotation = mock(GuestReviewGroupSession.class);
        given(annotation.required()).willReturn(true);
        given(methodParameter.getParameterAnnotation(GuestReviewGroupSession.class)).willReturn(annotation);

        // when, then
        assertThatThrownBy(() -> guestReviewGroupSessionResolver.resolveArgument(
                methodParameter, null, nativeWebRequest, null))
                .isInstanceOf(GuestReviewGroupSessionNotExistsException.class);
    }
}
