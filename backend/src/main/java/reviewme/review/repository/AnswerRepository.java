package reviewme.review.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("""
            SELECT a FROM Answer a
            JOIN Review r ON a.reviewId = r.id
            WHERE r.reviewGroupId = :reviewGroupId AND a.questionId IN :questionIds
            ORDER BY r.createdAt DESC
            LIMIT :limit
            """)
    List<Answer> findReceivedAnswersByQuestionIds(long reviewGroupId, Collection<Long> questionIds, int limit);

    @Query("""
            SELECT a.id FROM Answer a
            JOIN Review r
            ON a.reviewId = r.id
            WHERE r.reviewGroupId = :reviewGroupId
            """)
    Set<Long> findIdsByReviewGroupId(long reviewGroupId);

    @Query("""
            SELECT a.id FROM Answer a
            WHERE a.questionId = :questionId
            """)
    Set<Long> findIdsByQuestionId(long questionId);
}
