package reviewme.review.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reviewme.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
            SELECT r FROM Review r
            WHERE r.reviewGroupId = :reviewGroupId
            ORDER BY r.createdAt DESC
            """)
    List<Review> findAllByGroupId(long reviewGroupId);

    @Query("""
            SELECT r FROM Review r
            WHERE r.reviewGroupId = :reviewGroupId
            AND (:lastReviewId IS NULL OR r.id < :lastReviewId)
            ORDER BY r.createdAt DESC, r.id DESC
            LIMIT :limit
            """)
    List<Review> findByReviewGroupIdWithLimit(long reviewGroupId, Long lastReviewId, int limit);

    Optional<Review> findByIdAndReviewGroupId(long reviewId, long reviewGroupId);

    @Query("""
            SELECT COUNT(r.id) > 0 FROM Review r
            WHERE r.reviewGroupId = :reviewGroupId
            AND r.id < :reviewId
            AND CAST(r.createdAt AS java.time.LocalDate) <= :createdDate
            """)
    boolean existsOlderReviewInGroup(long reviewGroupId, long reviewId, LocalDate createdDate);

    int countByReviewGroupId(long reviewGroupId);
}
