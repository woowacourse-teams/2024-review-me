package reviewme.review.service.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.cache.TemplateCacheRepository;
import reviewme.question.domain.Question;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.exception.InvalidTextAnswerLengthException;

@Component
@RequiredArgsConstructor
public class TextAnswerValidator {

    private static final int MIN_LENGTH = 20;
    private static final int MAX_LENGTH = 1_000;

    private final TemplateCacheRepository templateCacheRepository;

    public void validate(TextAnswer textAnswer) {
        Question question = templateCacheRepository.findQuestionById(textAnswer.getQuestionId());
        validateLength(textAnswer, question);
    }

    private void validateLength(TextAnswer textAnswer, Question question) {
        int answerLength = textAnswer.getContent().length();

        if (question.isRequired() && (answerLength < MIN_LENGTH || answerLength > MAX_LENGTH)) {
            throw new InvalidTextAnswerLengthException(question.getId(), answerLength, MIN_LENGTH, MAX_LENGTH);
        }

        if (!question.isRequired() && answerLength > MAX_LENGTH) {
            throw new InvalidTextAnswerLengthException(question.getId(), answerLength, MAX_LENGTH);
        }
    }
}
