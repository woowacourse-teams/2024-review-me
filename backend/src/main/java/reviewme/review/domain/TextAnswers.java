package reviewme.review.domain;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import reviewme.review.service.exception.AnswerNotFoundByIdException;

@Slf4j
public class TextAnswers {

    private final Map<Long, TextAnswer> textAnswers;

    public TextAnswers(List<TextAnswer> textAnswers) {
        this.textAnswers = textAnswers.stream()
                .collect(Collectors.toMap(TextAnswer::getId, Function.identity()));
    }

    public TextAnswer get(long answerId) {
        if (!textAnswers.containsKey(answerId)) {
            throw new AnswerNotFoundByIdException(answerId);
        }
        return textAnswers.get(answerId);
    }
}
