package reviewme.review.service.dto.request;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EitherTextOrCheckboxValidator
        implements ConstraintValidator<EitherTextOrCheckbox, ReviewAnswerRequest> {

    @Override
    public boolean isValid(ReviewAnswerRequest request, ConstraintValidatorContext context) {
        if (request.selectedOptionIds() != null) {
            return request.text() == null;
        }
        return request.text() != null;
    }
}
