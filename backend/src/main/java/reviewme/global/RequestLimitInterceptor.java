package reviewme.global;

import static org.springframework.http.HttpHeaders.USER_AGENT;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import reviewme.config.RequestLimitProperties;
import reviewme.global.exception.TooManyRequestException;

@Component
@EnableConfigurationProperties(RequestLimitProperties.class)
@RequiredArgsConstructor
public class RequestLimitInterceptor implements HandlerInterceptor {

    private final RedisTemplate<String, Long> redisTemplate;
    private final RequestLimitProperties requestLimitProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!HttpMethod.POST.matches(request.getMethod())) {
            return true;
        }

        String key = generateRequestKey(request);
        ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
        valueOperations.setIfAbsent(key, 0L, requestLimitProperties.duration());
        redisTemplate.expire(key, requestLimitProperties.duration());

        long requestCount = valueOperations.increment(key);
        if (requestCount > requestLimitProperties.threshold()) {
            throw new TooManyRequestException(key);
        }
        return true;
    }

    private String generateRequestKey(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();
        String userAgent = request.getHeader(USER_AGENT);

        return String.format("RequestURI: %s, RemoteAddr: %s, UserAgent: %s", requestURI, remoteAddr, userAgent);
    }
}
