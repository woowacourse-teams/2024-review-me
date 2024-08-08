package reviewme.review.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.Review;
import reviewme.review.domain.exception.ReviewNotFoundException;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.reviewGroupId=:reviewGroupId ORDER BY r.createdAt DESC")
    List<Review> findReceivedReviewsByGroupId(long reviewGroupId);

    Optional<Review> findByIdAndReviewGroupId(long reviewId, long reviewGroupId);

    default Review getReviewById(Long id) {
        return findById(id).orElseThrow(() -> new ReviewNotFoundException(id));
    }
}
