package reviewme.review.service.dto.request;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OntTypeAnswerValidator
        implements ConstraintValidator<OneTypeAnswer, ReviewAnswerRequest> {

    @Override
    public boolean isValid(ReviewAnswerRequest request, ConstraintValidatorContext context) {
        if (request.selectedOptionIds() != null) {
            return request.text() == null;
        }
        return request.text() != null;
    }
}
