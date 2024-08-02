package reviewme.review.controller.validator;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContainsHeaderValidator.class)
public @interface ContainsHeaderName {

    @AliasFor("headerName")
    String value() default "";

    @AliasFor("value")
    String headerName() default "";

    String message() default "필수 값이 헤더에 존재하지 않아요";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
