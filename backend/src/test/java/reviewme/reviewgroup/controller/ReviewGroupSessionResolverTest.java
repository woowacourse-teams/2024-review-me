package reviewme.reviewgroup.controller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.NativeWebRequest;
import reviewme.reviewgroup.service.ReviewGroupService;

class ReviewGroupSessionResolverTest {

    private final ReviewGroupService reviewGroupService = mock(ReviewGroupService.class);

    private ReviewGroupSessionResolver reviewGroupSessionResolver;

    @BeforeEach
    void setUp() {
        reviewGroupSessionResolver = new ReviewGroupSessionResolver(reviewGroupService);
    }

    @Test
    void 세션에서_코드를_가져와_리뷰그룹으로_변환한다() {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("reviewRequestCode", "abcd");
        request.setSession(session);

        NativeWebRequest nativeWebRequest = mock(NativeWebRequest.class);
        given(nativeWebRequest.getNativeRequest(HttpServletRequest.class)).willReturn(request);

        // when
        assertDoesNotThrow(() -> reviewGroupSessionResolver.resolveArgument(
                null, null, nativeWebRequest, null
        ));
    }

    @Test
    void 세션이_존재하지_않는_경우_예외를_발생한다() {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        NativeWebRequest nativeWebRequest = mock(NativeWebRequest.class);
        given(nativeWebRequest.getNativeRequest(HttpServletRequest.class)).willReturn(request);

        // when, then
        assertThatThrownBy(() -> reviewGroupSessionResolver.resolveArgument(
                null, null, nativeWebRequest, null
        )).isInstanceOf(ReviewGroupSessionNotFoundException.class);
    }

    @Test
    void 세션에_코드가_없는_경우_예외를_발생한다() {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        NativeWebRequest nativeWebRequest = mock(NativeWebRequest.class);
        given(nativeWebRequest.getNativeRequest(HttpServletRequest.class)).willReturn(request);

        // when, then
        assertThatThrownBy(() -> reviewGroupSessionResolver.resolveArgument(
                null, null, nativeWebRequest, null
        )).isInstanceOf(ReviewGroupSessionNotFoundException.class);
    }
}
