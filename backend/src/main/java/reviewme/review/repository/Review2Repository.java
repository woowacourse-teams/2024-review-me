package reviewme.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.Review2;

@Repository
public interface Review2Repository extends JpaRepository<Review2, Long> {
}
