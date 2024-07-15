package reviewme.member;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewerGroupRepository extends JpaRepository<ReviewerGroup, Long> {

    default ReviewerGroup getReviewerGroupById(long id) {
        return findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
