package reviewme.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.ReviewContent;

@Repository
public interface ReviewContentRepository extends JpaRepository<ReviewContent, Long> {
}
