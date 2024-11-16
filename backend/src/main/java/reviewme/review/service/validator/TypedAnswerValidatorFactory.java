package reviewme.review.service.validator;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.review.domain.Answer;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TypedAnswerValidatorFactory {

    private final List<TypedAnswerValidator> validators;

    public TypedAnswerValidator getAnswerValidator(Class<? extends Answer> answerClass) {
        return validators.stream()
                .filter(validator -> validator.supports(answerClass))
                .findFirst()
                .orElseThrow(() -> new UnsupportedAnswerTypeException(answerClass));
    }
}
