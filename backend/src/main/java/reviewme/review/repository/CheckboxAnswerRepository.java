package reviewme.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewme.review.domain.CheckboxAnswer;

public interface CheckboxAnswerRepository extends JpaRepository<CheckboxAnswer, Long> {
}
