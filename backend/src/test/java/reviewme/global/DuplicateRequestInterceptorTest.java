package reviewme.global;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpHeaders.USER_AGENT;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import reviewme.global.exception.TooManyDuplicateRequestException;

class DuplicateRequestInterceptorTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final RedisTemplate<String, Long> redisTemplate = mock(RedisTemplate.class);
    private final ValueOperations<String, Long> valueOperations = mock(ValueOperations.class);
    private final DuplicateRequestInterceptor interceptor = new DuplicateRequestInterceptor(redisTemplate);
    private final String requestKey = "RequestURI: /api/v2/reviews, RemoteAddr: localhost, UserAgent: Postman";

    @BeforeEach
    void setUp() {
        given(request.getMethod()).willReturn("POST");

        given(request.getRequestURI()).willReturn("/api/v2/reviews");
        given(request.getRemoteAddr()).willReturn("localhost");
        given(request.getHeader(USER_AGENT)).willReturn("Postman");

        given(redisTemplate.opsForValue()).willReturn(valueOperations);
    }

    @Test
    void POST_요청이_아니면_통과한다() {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        given(request.getMethod()).willReturn("GET");

        // when
        boolean result = interceptor.preHandle(request, null, null);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 특정_POST_요청이_처음이_아니며_최대_빈도보다_작을_경우_빈도를_1증가시킨다() {
        // given
        long frequency = 1;
        given(valueOperations.get(anyString())).willReturn(frequency);

        // when
        boolean result = interceptor.preHandle(request, null, null);

        // then
        assertThat(result).isTrue();
        verify(valueOperations).increment(requestKey);
    }

    @Test
    void 특정_POST_요청이_처음이_아니며_최대_빈도보다_클_경우_예외를_발생시킨다() {
        // given
        long maxFrequency = 3;
        given(valueOperations.increment(anyString())).willReturn(maxFrequency + 1);

        // when & then
        assertThatThrownBy(() -> interceptor.preHandle(request, null, null))
                .isInstanceOf(TooManyDuplicateRequestException.class);
    }
}
