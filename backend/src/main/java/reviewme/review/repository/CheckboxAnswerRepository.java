package reviewme.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.CheckboxAnswer;

@Repository
public interface CheckboxAnswerRepository extends JpaRepository<CheckboxAnswer, Long> {
}
