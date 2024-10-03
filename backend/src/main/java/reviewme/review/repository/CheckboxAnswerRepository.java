package reviewme.review.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.CheckboxAnswer;

@Repository
public interface CheckboxAnswerRepository extends JpaRepository<CheckboxAnswer, Long> {

    @Query(value = """
            SELECT ca.* FROM checkbox_answer ca
            WHERE ca.review_id IN (:reviewIds)
            """, nativeQuery = true)
    List<CheckboxAnswer> findAllByReviewIds(List<Long> reviewIds);

    List<CheckboxAnswer> findAllByReviewId(long id);
}
