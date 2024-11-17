package reviewme.review.domain.policy;

import java.util.List;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.exception.InvalidTextAnswerLengthException;
import reviewme.review.domain.policy.TemplateValidationContext.QuestionProperties;

@Component
@Order(2)
public class TextAnswerRegistrationPolicy implements ReviewRegistrationPolicy {

    private static final int MIN_LENGTH = 20;
    private static final int MAX_LENGTH = 1_000;

    @Override
    public void verify(Review review, TemplateValidationContext templateContext) {
        List<TextAnswer> textAnswers = review.getAnswersByType(TextAnswer.class);

        for (TextAnswer textAnswer : textAnswers) {
            QuestionProperties questionProperties = templateContext.getQuestionPropertiesById(
                    textAnswer.getQuestionId());
            validateLength(textAnswer, questionProperties);
        }
    }

    private void validateLength(TextAnswer textAnswer, QuestionProperties questionProperties) {
        int answerLength = textAnswer.getContent().length();

        if (questionProperties.isRequired() && (answerLength < MIN_LENGTH || answerLength > MAX_LENGTH)) {
            throw new InvalidTextAnswerLengthException(questionProperties.getId(), answerLength, MIN_LENGTH,
                    MAX_LENGTH);
        }

        if (!questionProperties.isRequired() && answerLength > MAX_LENGTH) {
            throw new InvalidTextAnswerLengthException(questionProperties.getId(), answerLength, MAX_LENGTH);
        }
    }
}
