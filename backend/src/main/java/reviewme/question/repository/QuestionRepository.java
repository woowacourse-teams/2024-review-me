package reviewme.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewme.question.domain.Question2;
import reviewme.question.domain.exception.QuestionNotFoundException;

public interface QuestionRepository extends JpaRepository<Question2, Long> {

    default Question2 getQuestionById(long id) {
        return findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
    }
}
