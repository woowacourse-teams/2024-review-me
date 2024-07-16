package reviewme.review;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    default Review getReviewById(Long id) {
        return findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
