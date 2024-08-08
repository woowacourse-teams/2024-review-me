package reviewme.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.exception.DuplicateQuestionException;

@Component
@RequiredArgsConstructor
public class ReviewCreationQuestionValidator {

    void validate(List<Long> questionIds) {
        validateUniqueQuestion(questionIds);
    }

    private void validateUniqueQuestion(List<Long> questionIds) {
        int questionsCount = questionIds.size();
        long distinctCount = questionIds.stream()
                .distinct()
                .count();
        if (questionsCount != distinctCount) {
            throw new DuplicateQuestionException(questionIds);
        }
    }
}
