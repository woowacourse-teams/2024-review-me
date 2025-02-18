package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.ReviewFixture.비회원_작성_리뷰;
import static reviewme.fixture.ReviewGroupFixture.비회원_리뷰_그룹;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.ReceivedReviewPageResponse;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;

@ServiceTest
class ReviewListLookupServiceTest {

    @Autowired
    private ReviewListLookupService reviewListLookupService;

    @Autowired
    private ReviewGroupRepository reviewGroupRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void 확인_코드에_해당하는_그룹이_존재하면_내가_받은_리뷰_목록을_반환한다() {
        // given - 리뷰 그룹 저장
        ReviewGroup reviewGroup = reviewGroupRepository.save(비회원_리뷰_그룹());

        // given - 리뷰 답변 저장
        CheckboxAnswer categoryAnswer = new CheckboxAnswer(1L, List.of(1L));
        Review review1 = 비회원_작성_리뷰(1L, reviewGroup.getId(), List.of(categoryAnswer));
        TextAnswer textAnswer = new TextAnswer(1L, "텍스트형 응답");
        Review review2 = 비회원_작성_리뷰(1L, reviewGroup.getId(), List.of(textAnswer));
        reviewRepository.saveAll(List.of(review1, review2));

        // when
        ReceivedReviewPageResponse response = reviewListLookupService.getReceivedReviews(
                reviewGroup.getId(), Long.MAX_VALUE, 5
        );

        // then
        assertAll(
                () -> assertThat(response.reviews()).hasSize(2),
                () -> assertThat(response.lastReviewId()).isEqualTo(review1.getId()),
                () -> assertThat(response.isLastPage()).isTrue()
        );
    }

    @Test
    void 내가_받은_리뷰_목록을_페이지네이션을_적용하여_반환한다() {
        // given - 리뷰 그룹 저장
        ReviewGroup reviewGroup = reviewGroupRepository.save(비회원_리뷰_그룹());

        // given - 리뷰 답변 저장
        TextAnswer textAnswer = new TextAnswer(1L, "텍스트형 응답");
        Review review1 = 비회원_작성_리뷰(1L, reviewGroup.getId(), List.of(textAnswer));
        Review review2 = 비회원_작성_리뷰(1L, reviewGroup.getId(), List.of(textAnswer));
        Review review3 = 비회원_작성_리뷰(1L, reviewGroup.getId(), List.of(textAnswer));
        reviewRepository.saveAll(List.of(review1, review2, review3));

        // when
        ReceivedReviewPageResponse response
                = reviewListLookupService.getReceivedReviews(reviewGroup.getId(), Long.MAX_VALUE, 2);

        // then
        assertAll(
                () -> assertThat(response.reviews())
                        .hasSize(2)
                        .extracting("reviewId")
                        .containsExactly(review3.getId(), review2.getId()),
                () -> assertThat(response.lastReviewId())
                        .isEqualTo(review2.getId()),
                () -> assertThat(response.isLastPage())
                        .isFalse()
        );
    }
}
