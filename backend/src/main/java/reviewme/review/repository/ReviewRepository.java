package reviewme.review.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.member.domain.Member;
import reviewme.member.domain.ReviewerGroup;
import reviewme.review.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    default Review getReviewById(Long id) {
        return findById(id).orElseThrow(EntityNotFoundException::new);
    }

    boolean existsByReviewerAndReviewerGroup(Member reviewer, ReviewerGroup reviewerGroup);
}
