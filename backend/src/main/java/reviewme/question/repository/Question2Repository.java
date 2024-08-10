package reviewme.question.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.question.domain.Question2;
import reviewme.question.domain.exception.QuestionNotFoundException;

@Repository
public interface Question2Repository extends JpaRepository<Question2, Long> {

    Optional<Question2> findById(long id);

    default Question2 getQuestionById(long id) {
        return findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
    }
}
