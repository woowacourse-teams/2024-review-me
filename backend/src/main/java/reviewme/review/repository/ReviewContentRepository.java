package reviewme.review.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.ReviewContent;

@Repository
public interface ReviewContentRepository extends JpaRepository<ReviewContent, Long> {

    @Query(value = "SELECT * FROM review_content WHERE review_id = :reviewId", nativeQuery = true)
    List<ReviewContent> findAllByReviewId(@Param("reviewId") Long reviewId);
}
