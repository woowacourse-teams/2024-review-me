package reviewme.review.domain.policy;

import org.springframework.stereotype.Component;
import reviewme.review.domain.Answer;
import reviewme.review.domain.TextAnswer;
import reviewme.review.domain.exception.InvalidTextAnswerLengthException;
import reviewme.review.domain.policy.TemplateValidationContext.QuestionProperties;

@Component
public class TextAnswerRegistrationPolicy implements AnswerRegistrationPolicy {

    private static final int MIN_LENGTH = 20;
    private static final int MAX_LENGTH = 1_000;

    @Override
    public boolean support(Answer answer) {
        return answer instanceof TextAnswer;
    }

    @Override
    public void verify(Answer answer, TemplateValidationContext templateContext) {
        TextAnswer textAnswer = (TextAnswer) answer;

        QuestionProperties questionProperties = templateContext.getQuestionPropertiesById(
                textAnswer.getQuestionId());
        validateLength(textAnswer, questionProperties);
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
