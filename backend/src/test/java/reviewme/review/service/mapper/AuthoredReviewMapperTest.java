package reviewme.review.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.ReviewFixture.회원_작성_리뷰;
import static reviewme.fixture.ReviewGroupFixture.회원_리뷰_그룹;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.Review;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.AuthoredReviewElementResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;

@ServiceTest
class AuthoredReviewMapperTest {

    @Autowired
    private AuthoredReviewMapper reviewMapper;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Test
    void 리뷰_목록을_내가_작성한_리뷰_목록으로_매핑한다() {
        // given
        long memberId = 1L;
        ReviewGroup reviewGroup = reviewGroupRepository.save(회원_리뷰_그룹());
        Review review1 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup.getId(), List.of()));
        Review review2 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup.getId(), List.of()));
        Review review3 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup.getId(), List.of()));

        // when
        List<AuthoredReviewElementResponse> responses = reviewMapper.mapToReviewList(List.of(review1, review2, review3));

        // then
        assertThat(responses).extracting(AuthoredReviewElementResponse::reviewId)
                .contains(review1.getId(), review2.getId(), review3.getId());
    }
}
