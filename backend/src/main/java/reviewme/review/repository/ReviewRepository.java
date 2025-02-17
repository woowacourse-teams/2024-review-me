package reviewme.review.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reviewme.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
            SELECT r FROM Review r
            WHERE r.reviewGroupId = :reviewGroupId
            ORDER BY r.createdAt DESC, r.id DESC
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

    @Query("""
            SELECT r FROM Review r
            WHERE r.memberId = :memberId
            AND (:lastReviewId IS NULL OR r.id < :lastReviewRd)
            ORDER BY r.createdAt DESC, r.id DESC
            LIMIT :limit
            """)
    List<Review> findByMemberIdWithLimit(long memberId, Long lastReviewId, int limit);

    int countByReviewGroupId(long reviewGroupId);

    @Query("""
            SELECT COUNT(r.id) > 0 FROM Review r
            WHERE r.reviewGroupId = :reviewGroupId
            AND r.id < :reviewId
            AND r.createdAt <= :createdDate
            """)
    boolean existsOlderReviewInGroup(long reviewGroupId, long reviewId, LocalDateTime createdDate);

    @Query("""
            SELECT COUNT(r.id) > 0 FROM Review r
            WHERE r.memberId = :memberId
            AND r.id < :reviewId
            AND r.createdAt <= :createdDate
            """)
    boolean existsOlderReviewInMember(long memberId, long reviewId, LocalDateTime createdDate);
}
