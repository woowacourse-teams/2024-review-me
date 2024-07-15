package reviewme.review;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewKeywordRepository extends JpaRepository<ReviewKeyword, Long> {

    List<ReviewKeyword> findByReview(Review review);
}
