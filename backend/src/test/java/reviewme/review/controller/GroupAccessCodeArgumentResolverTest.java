package reviewme.review.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.NativeWebRequest;
import reviewme.review.controller.exception.MissingGroupAccessCodeException;

class GroupAccessCodeArgumentResolverTest {

    private final GroupAccessCodeArgumentResolver resolver = new GroupAccessCodeArgumentResolver();
    private final MethodParameter parameter = mock(MethodParameter.class);

    @BeforeEach
    void setUp() {
        given(parameter.hasParameterAnnotation(ValidGroupAccessCode.class)).willReturn(true);
    }

    @Test
    void 검증값이_헤더이름으로_존재하지_않으면_검증에_실패한다() {
        // given
        NativeWebRequest request = mock(NativeWebRequest.class);
        given(request.getNativeRequest()).willReturn(new MockHttpServletRequest());

        // when, then
        assertThatThrownBy(() -> resolver.resolveArgument(parameter, null, request, null))
                .isInstanceOf(MissingGroupAccessCodeException.class);
    }

    @Test
    void 검증값이_헤더에_존재하면_검증에_성공한다() {
        // given
        NativeWebRequest request = mock(NativeWebRequest.class);
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader("GroupAccessCode", "1234");
        given(request.getNativeRequest()).willReturn(mockRequest);

        // when
        String actual = resolver.resolveArgument(parameter, null, request, null);
        // then
        assertThat(actual).isEqualTo("1234");
    }
}
