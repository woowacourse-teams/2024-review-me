package reviewme.review.repository;

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
    List<Review> findAllByReviewGroupIdWithLimit(long reviewGroupId, Long lastReviewId, int limit);

    @Query("""
            SELECT r FROM Review r
            WHERE r.memberId = :memberId
            AND (:lastReviewId IS NULL OR r.id < :lastReviewId)
            ORDER BY r.createdAt DESC, r.id DESC
            LIMIT :limit
            """)
    List<Review> findAllByMemberIdWithLimit(long memberId, Long lastReviewId, int limit);

    int countByReviewGroupId(long reviewGroupId);
}
