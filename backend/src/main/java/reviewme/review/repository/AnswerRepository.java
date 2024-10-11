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

    @Query(value = """
            SELECT a FROM Answer a
            JOIN Review r ON a.reviewId = r.id
            WHERE r.reviewGroupId = :reviewGroupId AND a.questionId IN :questionIds
            ORDER BY r.createdAt DESC
            LIMIT :limit
            """)
    List<Answer> findReceivedAnswersByQuestionIds(long reviewGroupId, Collection<Long> questionIds, int limit);

    @Query(value = """
            SELECT a FROM Answer a
            JOIN Review r ON a.reviewId = r.id
            WHERE r.reviewGroupId = :reviewGroupId AND a.questionId IN :questionIds
            ORDER BY r.createdAt DESC
            """)
    List<Answer> findReceivedAnswersByQuestionIdsOrderByCreatedAtDesc(long reviewGroupId, Collection<Long> questionIds);

    @Query(value = """
            SELECT a FROM Answer a
            JOIN Review r ON a.reviewId = r.id
            WHERE r.reviewGroupId = :reviewGroupId AND a.questionId IN :questionIds
            """)
    List<Answer> findReceivedAnswersByQuestionIds(long reviewGroupId, Collection<Long> questionIds);

    @Query(value = """
            SELECT a FROM Answer a
            JOIN Review r ON a.reviewId = r.id
            WHERE r.reviewGroupId = :reviewGroupId AND a.questionId IN :questionIds
            """)
    List<Answer> findReceivedAnswersByQuestionIds(long reviewGroupId, List<Long> questionIds);

    @Query(value = """
            SELECT a FROM Answer a
            WHERE a.questionId IN :questionIds
            """)
    List<Answer> findAllByQuestionIds(List<Long> questionIds);

    @Query(value = """
            SELECT a.id FROM Answer a
            JOIN Review r
            ON a.reviewId = r.id
            WHERE r.reviewGroupId = :reviewGroupId
            """)
    Set<Long> findIdsByReviewGroupId(long reviewGroupId);

    @Query(value = """
            SELECT a FROM Answer a
            WHERE a.questionId IN :questionIds
            """)
    List<Answer> findAllByQuestions(List<Long> questionIds);

    @Query(value = """
            SELECT a FROM Answer a
            JOIN Review r
            ON a.reviewId = r.id
            WHERE r.reviewGroupId = :reviewGroupId
            """)
    Set<Answer> findAllByReviewGroupId(long reviewGroupId);

    @Query(value = """
            SELECT a.id FROM Answer a
            JOIN Question q
            ON a.questionId = q.id
            WHERE q.id = :questionId
            """)
    Set<Long> findIdsByQuestionId(long questionId);
}
