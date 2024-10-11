package reviewme.review.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reviewme.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = """
            SELECT r.* FROM new_review r
            WHERE r.review_group_id = :reviewGroupId
            ORDER BY r.created_at DESC
            """, nativeQuery = true)
    List<Review> findAllByGroupId(long reviewGroupId);

    @Query(value = """
            SELECT r.* FROM new_review r
            WHERE r.review_group_id = :reviewGroupId
            AND (:lastReviewId IS NULL OR r.id < :lastReviewId)
            ORDER BY r.created_at DESC, r.id DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<Review> findByReviewGroupIdWithLimit(long reviewGroupId, Long lastReviewId, int limit);

    Optional<Review> findByIdAndReviewGroupId(long reviewId, long reviewGroupId);

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

    int countByReviewGroupId(long reviewGroupId);
}
