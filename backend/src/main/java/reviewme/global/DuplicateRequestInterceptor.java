package reviewme.global;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import reviewme.global.exception.RequestFrequencyNonNumericException;
import reviewme.global.exception.TooManyDuplicateRequestException;

@Component
@RequiredArgsConstructor
public class DuplicateRequestInterceptor implements HandlerInterceptor {

    private static final int MAX_FREQUENCY = 3;
    private static final Duration DURATION_SECOND = Duration.ofSeconds(1);

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!HttpMethod.POST.matches(request.getMethod())) {
            return true;
        }

        String key = generateRequestKey(request);
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            redisTemplate.opsForValue().set(key, 1, DURATION_SECOND);
            return true;
        }

        if (!(value instanceof Integer)) {
            throw new RequestFrequencyNonNumericException(value);
        }
        int frequency = (int) value;
        if (frequency >= MAX_FREQUENCY) {
            throw new TooManyDuplicateRequestException(key);
        }
        redisTemplate.opsForValue().set(key, frequency + 1, DURATION_SECOND);
        return true;
    }

    private String generateRequestKey(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        return String.format("RequestURI: %s, RemoteAddr: %s, UserAgent: %s", requestURI, remoteAddr, userAgent);
    }
}
