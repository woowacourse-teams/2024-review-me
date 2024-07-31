package reviewme.review.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.question.domain.exception.DuplicateQuestionException;
import reviewme.question.domain.exception.QuestionNotFoundException;
import reviewme.review.repository.QuestionRepository;

@Component
@RequiredArgsConstructor
public class ReviewCreationQuestionValidator {

    private final QuestionRepository questionRepository;

    void validate(List<Long> questionIds) {
        validateUniqueQuestion(questionIds);
        validateExistsQuestion(questionIds);
    }

    private void validateUniqueQuestion(List<Long> questionIds) {
        int questionsCount = questionIds.size();
        long distinctCount = questionIds.stream()
                .distinct()
                .count();
        if (questionsCount != distinctCount) {
            throw new DuplicateQuestionException();
        }
    }

    private void validateExistsQuestion(List<Long> questionIds) {
        boolean doesQuestionExist = questionIds.stream()
                .anyMatch(questionRepository::existsById);
        if (!doesQuestionExist) {
            throw new QuestionNotFoundException();
        }
    }
}
