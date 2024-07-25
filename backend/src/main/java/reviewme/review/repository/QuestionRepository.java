package reviewme.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.Question;
import reviewme.review.exception.QuestionNotFoundException;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    default Question getQuestionById(long id) {
        return findById(id).orElseThrow(QuestionNotFoundException::new);
    }
}
