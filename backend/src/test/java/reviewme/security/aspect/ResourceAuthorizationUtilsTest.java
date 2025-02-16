package reviewme.security.aspect;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reviewme.security.aspect.exception.SpELEvaluationFailedException;
import reviewme.support.ServiceTest;

@ServiceTest
class ResourceAuthorizationUtilsTest {

    private ProceedingJoinPoint joinPoint;
    private MethodSignature signature;

    @BeforeEach
    void setUp() {
        joinPoint = mock(ProceedingJoinPoint.class);
        signature = mock(MethodSignature.class);
        given(joinPoint.getSignature()).willReturn(signature);
        given(signature.getMethod()).willReturn(TestRequest.class.getMethods()[0]);
    }

    @Nested
    class SpEL_표현식으로_값을_추출한다 {

        @Test
        void SpEL_과_일치하는_인자가_없으면_예외가_발생한다() {
            // given
            String[] parameterNames = {"reviewId"};
            Object[] args = {1L};
            given(signature.getParameterNames()).willReturn(parameterNames);
            given(joinPoint.getArgs()).willReturn(args);

            // when & then
            assertThatCode(() -> ResourceAuthorizationUtils.getTarget(joinPoint, "#wrong", Long.class))
                    .isInstanceOf(SpELEvaluationFailedException.class);
        }

        @Test
        void 인자_타입이_일치하지_않으면_예외가_발생한다() {
            // given
            String[] parameterNames = {"reviewId"};
            Object[] args = {1L};

            given(signature.getParameterNames()).willReturn(parameterNames);
            given(joinPoint.getArgs()).willReturn(args);

            // when & then
            assertThatCode(() -> ResourceAuthorizationUtils.getTarget(
                    joinPoint, "#reviewId", String.class
            )).isInstanceOf(SpELEvaluationFailedException.class);
        }

        @Test
        void 인자가_아예_없을_때_예외가_발생한다() {
            // given
            String[] parameterNames = {};
            Object[] args = {};

            given(signature.getParameterNames()).willReturn(parameterNames);
            given(joinPoint.getArgs()).willReturn(args);

            // when & then
            assertThatCode(() -> ResourceAuthorizationUtils.getTarget(
                    joinPoint, "#reviewId", Long.class
            )).isInstanceOf(SpELEvaluationFailedException.class);
        }

        @Test
        void 단일_표현식에_해당하는_값을_반환한다() {
            // given
            String[] parameterNames = {"reviewId"};
            Object[] args = {1L};
            given(signature.getParameterNames()).willReturn(parameterNames);
            given(joinPoint.getArgs()).willReturn(args);

            // when
            Long result = ResourceAuthorizationUtils.getTarget(
                    joinPoint, "#reviewId", Long.class
            );

            // then
            assertThat(result).isEqualTo(1L);
        }

        @Test
        void 메서드_호출_표현식에_해당하는_값을_반환한다() {
            // given
            String[] parameterNames = {"request"};
            TestRequest request = new TestRequest(1L);
            Object[] args = {request};

            given(signature.getParameterNames()).willReturn(parameterNames);
            given(joinPoint.getArgs()).willReturn(args);

            // when
            Long result = ResourceAuthorizationUtils.getTarget(
                    joinPoint, "#request.getId()", Long.class
            );

            // then
            assertThat(result).isEqualTo(1L);
        }
    }

    @Test
    void 현재_세션을_반환한다() {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("리뷰미", "파이팅");
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // when
        HttpSession actualSession = ResourceAuthorizationUtils.getCurrentSession();

        // then
        assertThat(actualSession.getAttribute("리뷰미")).isEqualTo("파이팅");
    }

    private static class TestRequest {
        Long id;

        public TestRequest(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }
}
