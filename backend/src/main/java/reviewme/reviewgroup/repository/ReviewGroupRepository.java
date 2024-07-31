package reviewme.reviewgroup.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.reviewgroup.domain.ReviewGroup;

@Repository
public interface ReviewGroupRepository extends JpaRepository<ReviewGroup, Long> {

    Optional<ReviewGroup> findByReviewRequestCode(String reviewRequestCode);
}
