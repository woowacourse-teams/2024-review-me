package reviewme.review.domain;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import reviewme.review.domain.exception.MissingTextAnswerForQuestionException;

@Slf4j
public class TextAnswers {

    private final Map<Long, TextAnswer> textAnswers;

    public TextAnswers(Collection<TextAnswer> textAnswers) {
        this.textAnswers = textAnswers.stream()
                .collect(Collectors.toMap(TextAnswer::getQuestionId, Function.identity()));
    }

    public TextAnswer getAnswerByQuestionId(long questionId) {
        if (!textAnswers.containsKey(questionId)) {
            throw new MissingTextAnswerForQuestionException(questionId);
        }
        return textAnswers.get(questionId);
    }

    public boolean hasAnswerByQuestionId(long questionId) {
        return textAnswers.containsKey(questionId);
    }
}
