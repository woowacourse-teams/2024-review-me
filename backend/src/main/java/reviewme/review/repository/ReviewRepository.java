package reviewme.review.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.review.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.reviewGroupId=:reviewGroupId ORDER BY r.createdAt DESC")
    List<Review> findReceivedReviewsByGroupId(long reviewGroupId);

    Optional<Review> findByIdAndReviewGroupId(long reviewId, long reviewGroupId);

    @Query(value = """
            SELECT r.* FROM review r
            INNER JOIN review_group rg
            ON rg.id = r.review_group_id
            WHERE r.id = :reviewId
            AND rg.review_request_code = :reviewRequestCode
            AND rg.group_access_code = :groupAccessCode
            """, nativeQuery = true)
    Optional<Review> findByIdAndCodes(long reviewId, String reviewRequestCode, String groupAccessCode);
}
