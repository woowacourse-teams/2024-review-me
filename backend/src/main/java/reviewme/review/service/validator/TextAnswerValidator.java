package reviewme.review.service.validator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.cache.TemplateCacheRepository;
import reviewme.question.domain.Question;
import reviewme.review.domain.Answer;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.exception.InvalidTextAnswerLengthException;

@Component
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TextAnswerValidator implements AnswerValidator {

    private static final int MIN_LENGTH = 20;
    private static final int MAX_LENGTH = 1_000;

    private final TemplateCacheRepository templateCacheRepository;

    @Override
    public boolean supports(Class<? extends Answer> answerClass) {
        return TextAnswer.class.isAssignableFrom(answerClass);
    }

    @Override
    public void validate(Answer answer) {
        TextAnswer textAnswer = (TextAnswer) answer;
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
