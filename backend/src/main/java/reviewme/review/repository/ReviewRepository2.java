package reviewme.review.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import reviewme.review.domain.Review2;

public interface ReviewRepository2 extends JpaRepository<Review2, Long> {

    Optional<Review2> findByIdAndReviewGroupId(long reviewId, long reviewGroupId);
}
