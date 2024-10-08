package reviewme.review.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reviewme.review.domain.TextAnswer;

public interface TextAnswerRepository extends JpaRepository<TextAnswer, Long> {
    @Query(value = """
            select ta.* from text_answer ta
            where ta.review_id in (:reviewIds)
            """, nativeQuery = true
    )
    List<TextAnswer> findByReviewIds(List<Long> reviewIds);
}
