package reviewme.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewme.review.domain.CheckboxAnswer;

public interface NewCheckboxAnswerRepository extends JpaRepository<CheckboxAnswer, Long> {
}
