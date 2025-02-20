package reviewme.security.aspect;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reviewme.security.aspect.exception.SpELEvaluationFailedException;

public class ResourceAuthorizationUtils {

    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    public static <T> T getTarget(ProceedingJoinPoint joinPoint, String targetExpression, Class<T> targetType) {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        StandardEvaluationContext context = new StandardEvaluationContext();
        String[] paramNames = signature.getParameterNames();
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }

        try {
            Expression expression = EXPRESSION_PARSER.parseExpression(targetExpression);
            TypeDescriptor actualTypeDescriptor = expression.getValueTypeDescriptor(context);
            TypeDescriptor expectedTypeDescriptor = TypeDescriptor.valueOf(targetType);
            if (actualTypeDescriptor == null || !expectedTypeDescriptor.isAssignableTo(actualTypeDescriptor)) {
                throw new SpELEvaluationFailedException(signature.getMethod().getName(), targetExpression);
            }
            T targetValue = expression.getValue(context, targetType);
            return Objects.requireNonNull(targetValue);
        } catch (Exception e) {
            throw new SpELEvaluationFailedException(signature.getMethod().getName(), targetExpression);
        }
    }

    public static HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }
}
