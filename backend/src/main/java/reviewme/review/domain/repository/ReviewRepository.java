package reviewme.review.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reviewme.review.repository.ReviewJpaRepository;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final ReviewJpaRepository jpaRepository;
}

