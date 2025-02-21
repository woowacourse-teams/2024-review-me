package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.ReviewFixture.비회원_작성_리뷰;
import static reviewme.fixture.ReviewFixture.회원_작성_리뷰;
import static reviewme.fixture.ReviewGroupFixture.비회원_리뷰_그룹;
import static reviewme.fixture.ReviewGroupFixture.회원_리뷰_그룹;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.review.domain.CheckboxAnswer;
import reviewme.review.domain.Review;
import reviewme.review.domain.TextAnswer;
import reviewme.review.repository.ReviewRepository;
import reviewme.review.service.dto.response.list.AuthoredReviewElementResponse;
import reviewme.review.service.dto.response.list.AuthoredReviewsResponse;
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

    @Test
    void 내가_작성한_리뷰_목록을_페이지네이션을_적용하여_반환한다() {
        // given - 회원 설정
        long memberId = 1L;

        // given - 리뷰 그룹 설정
        ReviewGroup reviewGroup1 = reviewGroupRepository.save(회원_리뷰_그룹());
        Review review1 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup1.getId(), List.of()));
        Review review2 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup1.getId(), List.of()));

        ReviewGroup reviewGroup2 = reviewGroupRepository.save(회원_리뷰_그룹());
        Review review3 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup2.getId(), List.of()));
        Review review4 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup2.getId(), List.of()));
        Review review5 = reviewRepository.save(회원_작성_리뷰(memberId, 1L, reviewGroup2.getId(), List.of()));

        // given - 페이지네이션 설정
        long lastReviewId = review4.getId();
        int size = 2;

        // when
        AuthoredReviewsResponse response = reviewListLookupService.getAuthoredReviews(memberId, lastReviewId, size);

        // then
        assertAll(
                () -> assertThat(response.lastReviewId()).isEqualTo(reviewGroup2.getId()),
                () -> assertThat(response.isLastPage()).isFalse(),
                () -> assertThat(response.reviews()).extracting(AuthoredReviewElementResponse::reviewId)
                        .containsExactly(review3.getId(), review2.getId())
        );
    }
}
