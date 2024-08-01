package reviewme.review.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.Review;
import reviewme.review.domain.exception.ReviewNotFoundException;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByReviewGroupId(long id);

    default Review getReviewById(Long id) {
        return findById(id).orElseThrow(ReviewNotFoundException::new);
    }
}
