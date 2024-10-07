package reviewme.review.domain.abstraction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewReviewRepository extends JpaRepository<NewReview, Long> {

    @Query(value = """
            SELECT r.* FROM new_review r
            WHERE r.review_group_id = :reviewGroupId
            ORDER BY r.created_at DESC 
            """, nativeQuery = true)
    List<NewReview> findAllByGroupId(long reviewGroupId);

    @Query(value = """
            SELECT r.* FROM new_review r
            WHERE r.review_group_id = :reviewGroupId
            AND (:lastReviewId IS NULL OR r.id < :lastReviewId)
            ORDER BY r.created_at DESC, r.id DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<NewReview> findByReviewGroupIdWithLimit(long reviewGroupId, Long lastReviewId, int limit);

    @Query("""
       SELECT DISTINCT r FROM NewReview r
       JOIN FETCH r.answers a
       WHERE r.id = :reviewId AND r.reviewGroupId = :reviewGroupId
       """)
    Optional<NewReview> findByIdAndReviewGroupId(long reviewId, long reviewGroupId);

    @Query(value = """
            SELECT COUNT(r.id) FROM new_review r
            WHERE r.review_group_id = :reviewGroupId
            AND r.id < :reviewId
            AND CAST(r.created_at AS DATE) <= :createdDate
            """, nativeQuery = true)
    Long existsOlderReviewInGroupInLong(long reviewGroupId, long reviewId, LocalDate createdDate);

    default boolean existsOlderReviewInGroup(long reviewGroupId, long reviewId, LocalDate createdDate) {
        return existsOlderReviewInGroupInLong(reviewGroupId, reviewId, createdDate) > 0;
    }
}
