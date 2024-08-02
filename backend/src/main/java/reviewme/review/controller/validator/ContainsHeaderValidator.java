package reviewme.review.controller.validator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContainsHeaderValidator implements ConstraintValidator<ContainsHeaderName, HttpServletRequest> {

    private String headerName;

    @Override
    public void initialize(ContainsHeaderName constraintAnnotation) {
        this.headerName = constraintAnnotation.headerName();
    }

    @Override
    public boolean isValid(HttpServletRequest request, ConstraintValidatorContext context) {
        return request.getHeader(headerName) != null;
    }
}
