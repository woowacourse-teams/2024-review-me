package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.KeywordFixture.추진력이_좋아요;
import static reviewme.fixture.QuestionFixure.소프트스킬이_어떤가요;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.keyword.domain.Keyword;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.question.domain.Question;
import reviewme.review.dto.request.CreateReviewContentRequest;
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.repository.QuestionRepository;
import reviewme.review.repository.ReviewContentRepository;
import reviewme.review.repository.ReviewKeywordRepository;
import reviewme.review.repository.ReviewRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.repository.ReviewGroupRepository;
import reviewme.support.ServiceTest;

@ServiceTest
class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    KeywordRepository keywordRepository;

    @Autowired
    ReviewContentRepository reviewContentRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ReviewGroupRepository reviewGroupRepository;

    @Autowired
    ReviewKeywordRepository reviewKeywordRepository;

    @Test
    void 리뷰를_생성한다() {
        // given
        String reviewRequestCode = "reviewRequestCode";
        Keyword keyword = keywordRepository.save(추진력이_좋아요.create());
        Question question = questionRepository.save(소프트스킬이_어떤가요.create());
        reviewGroupRepository.save(new ReviewGroup("산초", "리뷰미 프로젝트", reviewRequestCode, "groupAccessCode"));

        CreateReviewContentRequest contentRequest = new CreateReviewContentRequest(question.getId(), "답변".repeat(20));
        CreateReviewRequest reviewRequest = new CreateReviewRequest(reviewRequestCode, List.of(contentRequest),
                List.of(keyword.getId()));

        // when
        long reviewId = reviewService.createReview(reviewRequest);

        // then
        assertAll(
                () -> assertThat(reviewRepository.findById(reviewId).isPresent()).isTrue(),
                () -> assertThat(reviewContentRepository.findAllByReviewId(reviewId)).isNotEmpty(),
                () -> assertThat(reviewKeywordRepository.findByReviewId(reviewId).isPresent()).isTrue()
        );
    }
}
