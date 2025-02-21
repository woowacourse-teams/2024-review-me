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
import reviewme.auth.domain.GitHubMember;
import reviewme.security.resolver.dto.LoginMember;
import reviewme.security.resolver.exception.LoginMemberSessionNotExistsException;
import reviewme.security.session.SessionManager;

@ExtendWith(MockitoExtension.class)
class LoginMemberSessionResolverTest {

    @InjectMocks
    private LoginMemberSessionResolver loginMemberSessionResolver;

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
    void 세션을_통해_로그인_회원을_반환한다() {
        // given
        GitHubMember gitHubMember = new GitHubMember(1L, "name", "url");

        given(nativeWebRequest.getNativeRequest(HttpServletRequest.class)).willReturn(httpServletRequest);
        given(sessionManager.getGitHubMember(httpServletRequest)).willReturn(gitHubMember);

        LoginMemberSession annotation = mock(LoginMemberSession.class);
        given(annotation.required()).willReturn(false);
        given(methodParameter.getParameterAnnotation(LoginMemberSession.class)).willReturn(annotation);

        // when
        LoginMember actual = (LoginMember) loginMemberSessionResolver.resolveArgument(
                methodParameter, null, nativeWebRequest, null);

        // then
        assertThat(actual.id()).isEqualTo(gitHubMember.getMemberId());
    }

    @Test
    void 어노테이션의_속성이_flase일때_세션이_없다면_null을_반환한다() {
        // given
        given(nativeWebRequest.getNativeRequest(HttpServletRequest.class)).willReturn(httpServletRequest);

        LoginMemberSession annotation = mock(LoginMemberSession.class);
        given(annotation.required()).willReturn(false);
        given(methodParameter.getParameterAnnotation(LoginMemberSession.class)).willReturn(annotation);

        // when
        LoginMember actual = (LoginMember) loginMemberSessionResolver.resolveArgument(
                methodParameter, null, nativeWebRequest, null);

        // then
        assertThat(actual).isNull();
    }

    @Test
    void 어노테이션의_속성이_false일때_세션에_저장된_데이터가_없다면_null을_반환한다() {
        // given
        given(nativeWebRequest.getNativeRequest(HttpServletRequest.class)).willReturn(httpServletRequest);
        given(sessionManager.getGitHubMember(httpServletRequest)).willReturn(null);

        LoginMemberSession annotation = mock(LoginMemberSession.class);
        given(annotation.required()).willReturn(false);
        given(methodParameter.getParameterAnnotation(LoginMemberSession.class)).willReturn(annotation);

        // when
        LoginMember actual = (LoginMember) loginMemberSessionResolver.resolveArgument(
                methodParameter, null, nativeWebRequest, null);

        // then
        assertThat(actual).isNull();
    }

    @Test
    void 어노테이션의_속성이_true일때_세션이_없다면_예외가_발생한다() {
        // given
        given(nativeWebRequest.getNativeRequest(HttpServletRequest.class)).willReturn(httpServletRequest);

        LoginMemberSession annotation = mock(LoginMemberSession.class);
        given(annotation.required()).willReturn(true);
        given(methodParameter.getParameterAnnotation(LoginMemberSession.class)).willReturn(annotation);

        // when, then
        assertThatThrownBy(() -> loginMemberSessionResolver.resolveArgument(
                methodParameter, null, nativeWebRequest, null))
                .isInstanceOf(LoginMemberSessionNotExistsException.class);
    }

    @Test
    void 어노테이션의_속성이_true일때_세션에_저장된_데이터가_없다면_예외가_발생한다() {
        // given
        given(nativeWebRequest.getNativeRequest(HttpServletRequest.class)).willReturn(httpServletRequest);
        given(sessionManager.getGitHubMember(httpServletRequest)).willReturn(null);

        LoginMemberSession annotation = mock(LoginMemberSession.class);
        given(annotation.required()).willReturn(true);
        given(methodParameter.getParameterAnnotation(LoginMemberSession.class)).willReturn(annotation);

        // when, then
        assertThatThrownBy(() -> loginMemberSessionResolver.resolveArgument(
                methodParameter, null, nativeWebRequest, null))
                .isInstanceOf(LoginMemberSessionNotExistsException.class);
    }
}
