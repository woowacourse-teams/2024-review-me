package reviewme.review.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.ReviewGroupFixture.리뷰_그룹;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.ReceivedReviewPageElementResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;

@ServiceTest
class ReviewListMapperTest {

    @Autowired
    private ReviewListMapper reviewListMapper;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 리뷰_그룹에_있는_리뷰를_반환한다() {
        // given - 리뷰 그룹
        ReviewGroup reviewGroup = reviewGroupRepository.save(리뷰_그룹());

        // given - 리뷰 답변 저장
        TextAnswer textAnswer = new TextAnswer(1L, "텍스트형 응답");
        Review review1 = new Review(1L, reviewGroup.getId(), List.of(textAnswer));
        Review review2 = new Review(1L, reviewGroup.getId(), List.of(textAnswer));
        Review review3 = new Review(1L, reviewGroup.getId(), List.of(textAnswer));
        Review review4 = new Review(1L, reviewGroup.getId(), List.of(textAnswer));
        Review review5 = new Review(1L, reviewGroup.getId(), List.of(textAnswer));
        Review review6 = new Review(1L, reviewGroup.getId(), List.of(textAnswer));
        Review review7 = new Review(1L, reviewGroup.getId(), List.of(textAnswer));
        Review review8 = new Review(1L, reviewGroup.getId(), List.of(textAnswer));
        Review review9 = new Review(1L, reviewGroup.getId(), List.of(textAnswer));
        Review review10 = new Review(1L, reviewGroup.getId(), List.of(textAnswer));
        reviewRepository.saveAll(
                List.of(review1, review2, review3, review4, review5, review6, review7, review8, review9, review10));

        long lastReviewId = 8L;
        int size = 5;

        // when
        List<ReceivedReviewPageElementResponse> responses = reviewListMapper.mapToReviewList(
                reviewGroup, lastReviewId, size);

        // then
        assertAll(
                () -> assertThat(responses).hasSize(size),
                () -> assertThat(responses).extracting(ReceivedReviewPageElementResponse::reviewId)
                        .containsExactly(
                                review7.getId(), review6.getId(), review5.getId(), review4.getId(), review3.getId())
        );
    }
}
