package reviewme.review.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import reviewme.review.domain.Review;

public interface ReviewRepository2 extends JpaRepository<Review, Long> {

    Optional<Review> findByIdAndReviewGroupId(long reviewId, long reviewGroupId);
}
