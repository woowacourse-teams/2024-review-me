package reviewme.reviewgroup.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.domain.exception.InvalidReviewRequestCodeException;

@Repository
public interface ReviewGroupRepository extends JpaRepository<ReviewGroup, Long> {

    default ReviewGroup getReviewGroupByReviewRequestCode(String reviewRequestCode) {
        return findByReviewRequestCode(reviewRequestCode)
                .orElseThrow(InvalidReviewRequestCodeException::new);
    }

    Optional<ReviewGroup> findByReviewRequestCode(String reviewRequestCode);
}
