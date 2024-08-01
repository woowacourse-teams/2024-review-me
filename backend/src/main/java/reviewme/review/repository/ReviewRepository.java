package reviewme.review.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.Review;
import reviewme.review.domain.exception.ReviewNotFoundException;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    default Review getReviewById(Long id) {
        return findById(id).orElseThrow(ReviewNotFoundException::new);
    }

    Optional<Review> findByIdAndReviewGroupId(long reviewId, long reviewGroupId);
}
