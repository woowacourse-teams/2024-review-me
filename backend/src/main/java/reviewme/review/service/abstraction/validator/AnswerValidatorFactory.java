package reviewme.review.service.abstraction.validator;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.review.domain.abstraction.Answer;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerValidatorFactory {

    private final List<NewAnswerValidator> answerValidators;

    public NewAnswerValidator getAnswerValidator(Class<? extends Answer> answerClass) {
        return answerValidators.stream()
                .filter(validator -> validator.supports(answerClass))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported answer class: " + answerClass));
    }
}
