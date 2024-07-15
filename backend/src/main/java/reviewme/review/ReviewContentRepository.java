package reviewme.review;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewContentRepository extends JpaRepository<ReviewContent, Long> {

    List<ReviewContent> findByReview(Review review);
}
