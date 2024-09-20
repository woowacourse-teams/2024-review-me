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
            WHERE r.reviewGroupId = :reviewGroupId
            ORDER BY r.createdAt DESC 
            """, nativeQuery = true)
    List<Review> findAllByGroupId(long reviewGroupId);

    Optional<Review> findByIdAndReviewGroupId(long reviewId, long reviewGroupId);
}
