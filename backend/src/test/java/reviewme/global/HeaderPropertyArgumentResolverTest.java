package reviewme.global;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.NativeWebRequest;
import reviewme.global.exception.MissingHeaderPropertyException;

class HeaderPropertyArgumentResolverTest {

    private final HeaderPropertyArgumentResolver resolver = new HeaderPropertyArgumentResolver();
    private final MethodParameter parameter = mock(MethodParameter.class);
    private final HeaderProperty headerProperty = mock(HeaderProperty.class);

    @BeforeEach
    void setUp() {
        given(parameter.hasParameterAnnotation(HeaderProperty.class)).willReturn(true);
        given(parameter.getParameterAnnotation(HeaderProperty.class)).willReturn(headerProperty);
    }

    @Test
    void 검증값이_헤더에_존재하지_않으면_검증에_실패한다() {
        // given
        NativeWebRequest request = mock(NativeWebRequest.class);
        given(request.getNativeRequest()).willReturn(new MockHttpServletRequest());
        given(headerProperty.headerName()).willReturn("test");

        // when, then
        assertThatThrownBy(() -> resolver.resolveArgument(parameter, null, request, null))
                .isInstanceOf(MissingHeaderPropertyException.class);
    }

    @Test
    void 검증값이_헤더에_존재하면_값을_반환한다() {
        // given
        String headerName = "test";
        String headerValue = "1234";
        NativeWebRequest request = mock(NativeWebRequest.class);
        MockHttpServletRequest mockRequest = (new MockHttpServletRequest());
        mockRequest.addHeader(headerName, headerValue);
        given(request.getNativeRequest()).willReturn(mockRequest);
        given(headerProperty.headerName()).willReturn(headerName);

        // when
        String actual = resolver.resolveArgument(parameter, null, request, null);

        // then
        assertThat(actual).isEqualTo(headerValue);
    }
}
