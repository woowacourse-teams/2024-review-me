package reviewme.review.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.ReviewContent;

@Repository
public interface ReviewContentRepository extends JpaRepository<ReviewContent, Long> {

    List<ReviewContent> findAllByReviewId(long reviewId);
}
