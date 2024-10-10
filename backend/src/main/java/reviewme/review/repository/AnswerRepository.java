package reviewme.review.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query(value = """
            select a.id from answer a
            join review r
            on a.review_id = r.id
            where r.review_group_id = :reviewGroupId
            """, nativeQuery = true)
    Set<Long> findIdsByReviewGroupId(long reviewGroupId);

    @Query(value = """
            select a.id from answer a 
            join question q 
            on a.question_id = q.id 
            where q.id = :questionId
            """, nativeQuery = true)
    Set<Long> findIdsByQuestionId(long questionId);
}
