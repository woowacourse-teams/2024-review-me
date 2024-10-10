package reviewme.review.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query(value = """
            SELECT a.id FROM Answer a
            JOIN Review r
            ON a.reviewId = r.id
            WHERE r.reviewGroupId = :reviewGroupId
            """)
    Set<Long> findIdsByReviewGroupId(long reviewGroupId);

    @Query(value = """
            SELECT a.id FROM Answer a
            JOIN Question q
            ON a.questionId = q.id
            WHERE q.id = :questionId
            """)
    Set<Long> findIdsByQuestionId(long questionId);
}
