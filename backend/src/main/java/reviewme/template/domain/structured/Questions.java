package reviewme.template.domain.structured;

import java.util.List;
import reviewme.template.domain.Question;
import reviewme.template.domain.exception.QuestionNotExistException;

public class Questions {

    private final List<Question> questions;

    public Questions(List<Question> questions) {
        validateQuestions(questions);
        this.questions = questions.stream()
                .distinct()
                .toList();
    }

    private void validateQuestions(List<Question> questions) {
        if (questions == null || questions.isEmpty()) {
            throw new QuestionNotExistException();
        }
    }

    public List<Long> getQuestionIds() {
        return questions.stream()
                .map(Question::getId)
                .toList();
    }

    public List<Long> getCheckBoxQuestionIds() {
        return questions.stream()
                .filter(Question::isCheckboxType)
                .map(Question::getId)
                .toList();
    }

    public boolean hasCheckboxQuestion() {
        return questions.stream()
                .anyMatch(Question::isCheckboxType);
    }
}
