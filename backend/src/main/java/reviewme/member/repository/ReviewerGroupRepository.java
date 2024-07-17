package reviewme.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewme.member.domain.ReviewerGroup;
import reviewme.member.exception.ReviewerGroupNotFoundException;

@Repository
public interface ReviewerGroupRepository extends JpaRepository<ReviewerGroup, Long> {

    default ReviewerGroup getReviewerGroupById(long id) {
        return findById(id).orElseThrow(ReviewerGroupNotFoundException::new);
    }
}
