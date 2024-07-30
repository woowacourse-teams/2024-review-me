package reviewme.review.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.ReviewKeyword;

@Repository
public interface ReviewKeywordRepository extends JpaRepository<ReviewKeyword, Long> {

    Optional<ReviewKeyword> findByReviewId(long reviewId);
}
