package reviewme.review.domain;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import reviewme.review.domain.exception.MissingCheckboxAnswerForQuestionException;

public class CheckboxAnswers {

    private final Map<Long, CheckboxAnswer> checkboxAnswers;

    public CheckboxAnswers(List<CheckboxAnswer> checkboxAnswers) {
        this.checkboxAnswers = checkboxAnswers.stream()
                .collect(Collectors.toMap(CheckboxAnswer::getQuestionId, Function.identity()));
    }

    public CheckboxAnswer getAnswerByQuestionId(long questionId) {
        if (!checkboxAnswers.containsKey(questionId)) {
            throw new MissingCheckboxAnswerForQuestionException(questionId);
        }
        return checkboxAnswers.get(questionId);
    }

    public boolean hasAnswerByQuestionId(long questionId) {
        return checkboxAnswers.containsKey(questionId);
    }
}
