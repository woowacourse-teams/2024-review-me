package reviewme.review.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reviewme.member.domain.Member;
import reviewme.member.domain.ReviewerGroup;
import reviewme.review.domain.Review;
import reviewme.review.exception.ReviewNotFoundException;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByReviewerAndReviewerGroup(Member reviewer, ReviewerGroup reviewerGroup);

    default Review getReviewById(Long id) {
        return findById(id).orElseThrow(ReviewNotFoundException::new);
    }

    @Query("""
            SELECT r
            FROM Review r
            WHERE r.reviewee.id = :revieweeId
            AND r.id < :lastViewedReviewId
            ORDER BY r.createdAt DESC
            LIMIT :size
            """
    )
    List<Review> findAllByRevieweeBeforeLastViewedReviewId(long revieweeId, long lastViewedReviewId, int size);
}
