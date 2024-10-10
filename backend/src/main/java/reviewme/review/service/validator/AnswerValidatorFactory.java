package reviewme.review.service.validator;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.review.domain.Answer;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerValidatorFactory {

    private final List<AnswerValidator> answerValidators;

    public AnswerValidator getAnswerValidator(Class<? extends Answer> answerClass) {
        return answerValidators.stream()
                .filter(validator -> validator.supports(answerClass))
                .findFirst()
                .orElseThrow(() -> new UnsupportedAnswerTypeException(answerClass));
    }
}
