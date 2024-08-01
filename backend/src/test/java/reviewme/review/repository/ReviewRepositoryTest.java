package reviewme.review.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.review.domain.Review;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 리뷰_목록을_최신_순서대로_조회한다() {
        // given
        Review review1 = reviewRepository.save(new Review(1, List.of(), LocalDateTime.of(2021, 1, 1, 0, 0)));
        Review review2 = reviewRepository.save(new Review(1, List.of(), LocalDateTime.of(2022, 1, 1, 0, 0)));
        Review review3 = reviewRepository.save(new Review(1, List.of(), LocalDateTime.of(2000, 1, 1, 0, 0)));
        Review review4 = reviewRepository.save(new Review(1, List.of(), LocalDateTime.of(2024, 1, 1, 0, 0)));

        // when
        List<Review> actual = reviewRepository.findReceivedReviewsByGroupId(1);

        // then
        assertThat(actual).map(Review::getId)
            .containsExactly(review4.getId(), review2.getId(), review1.getId(), review3.getId());
    }
}
