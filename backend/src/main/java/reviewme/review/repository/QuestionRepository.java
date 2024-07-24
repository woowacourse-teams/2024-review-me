package reviewme.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
