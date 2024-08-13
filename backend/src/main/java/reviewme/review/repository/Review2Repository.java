package reviewme.review.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.Review;

@Repository
public interface Review2Repository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.reviewGroupId=:reviewGroupId ORDER BY r.createdAt DESC")
    List<Review> findReceivedReviewsByGroupId(long reviewGroupId);
}
