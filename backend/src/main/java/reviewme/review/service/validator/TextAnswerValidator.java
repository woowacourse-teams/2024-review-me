package reviewme.review.service.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.Question;
import reviewme.question.repository.QuestionRepository;
import reviewme.review.domain.TextAnswer;
import reviewme.review.service.exception.InvalidTextAnswerLengthException;
import reviewme.review.service.exception.SubmittedQuestionNotFoundException;

@Component
@RequiredArgsConstructor
public class TextAnswerValidator {

    private static final int ZERO_LENGTH = 0;
    private static final int MIN_LENGTH = 20;
    private static final int MAX_LENGTH = 1_000;

    private final QuestionRepository questionRepository;

    public void validate(TextAnswer textAnswer) {
        Question question = questionRepository.findById(textAnswer.getQuestionId())
                .orElseThrow(() -> new SubmittedQuestionNotFoundException(textAnswer.getQuestionId()));

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
