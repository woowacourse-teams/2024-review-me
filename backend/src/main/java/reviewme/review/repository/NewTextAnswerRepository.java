package reviewme.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewme.review.domain.NewTextAnswer;

public interface NewTextAnswerRepository extends JpaRepository<NewTextAnswer, Long> {
}
