package reviewme.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.TextAnswer;

@Repository
public interface TextAnswerRepository extends JpaRepository<TextAnswer, Long> {
}
