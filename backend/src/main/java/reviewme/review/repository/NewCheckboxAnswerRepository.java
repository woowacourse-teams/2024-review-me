package reviewme.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewme.review.domain.NewCheckboxAnswer;

public interface NewCheckboxAnswerRepository extends JpaRepository<NewCheckboxAnswer, Long> {
}
