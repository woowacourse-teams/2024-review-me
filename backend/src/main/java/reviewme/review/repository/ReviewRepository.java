package reviewme.review.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = """
            SELECT r.* FROM review r
            WHERE r.review_group_id = :reviewGroupId
            ORDER BY r.created_at DESC 
            """, nativeQuery = true)
    List<Review> findAllByGroupId(long reviewGroupId);

    @Query(value = """
            SELECT r.* FROM review r
            WHERE r.review_group_id = :reviewGroupId
            AND (:lastReviewId IS NULL OR r.id < :lastReviewId)
            ORDER BY r.created_at DESC 
            LIMIT :limit
            """, nativeQuery = true)
    List<Review> findByReviewGroupIdWithLimit(long reviewGroupId, Long lastReviewId, int limit);

    Optional<Review> findByIdAndReviewGroupId(long reviewId, long reviewGroupId);

    int countByReviewGroupId(long id);
}
