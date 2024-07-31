package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.KeywordFixture.추진력이_좋아요;
import static reviewme.fixture.KeywordFixture.회의를_이끌어요;
import static reviewme.fixture.QuestionFixure.기술역량이_어떤가요;
import static reviewme.fixture.QuestionFixure.소프트스킬이_어떤가요;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.keyword.domain.Keyword;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.question.domain.Question;
import reviewme.review.domain.Review;
import reviewme.review.dto.request.CreateReviewContentRequest;
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.dto.response.ReviewSetupResponse;
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
        Keyword keyword1 = keywordRepository.save(추진력이_좋아요.create());
        Keyword keyword2 = keywordRepository.save(회의를_이끌어요.create());
        Question question1 = questionRepository.save(소프트스킬이_어떤가요.create());
        Question question2 = questionRepository.save(기술역량이_어떤가요.create());

        String reviewRequestCode = "reviewRequestCode";
        reviewGroupRepository.save(new ReviewGroup("산초", "리뷰미 프로젝트", reviewRequestCode, "groupAccessCode"));

        CreateReviewContentRequest request1 = new CreateReviewContentRequest(question1.getId(), "답변".repeat(20));
        CreateReviewContentRequest request2 = new CreateReviewContentRequest(question2.getId(), "응답".repeat(20));
        CreateReviewRequest reviewRequest1 = new CreateReviewRequest(reviewRequestCode, List.of(request1, request2),
                List.of(keyword1.getId(), keyword2.getId()));
        CreateReviewRequest reviewRequest2 = new CreateReviewRequest(reviewRequestCode, List.of(request1),
                List.of(keyword1.getId()));

        // when
        long reviewId = reviewService.createReview(reviewRequest1);
        reviewService.createReview(reviewRequest2);

        // then
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        assertThat(optionalReview).isPresent();
        assertAll(
                () -> assertThat(optionalReview.get().getReviewContents()).hasSize(2),
                () -> assertThat(reviewKeywordRepository.findAllByReviewId(reviewId)).hasSize(2)
        );
    }

    @Test
    void 리뷰_작성을_위해_필요한_정보를_조회한다() {
        // given
        Keyword keyword1 = keywordRepository.save(추진력이_좋아요.create());
        Keyword keyword2 = keywordRepository.save(회의를_이끌어요.create());
        Question question1 = questionRepository.save(소프트스킬이_어떤가요.create());
        Question question2 = questionRepository.save(기술역량이_어떤가요.create());

        String reviewee = "테드";
        String projectName = "리뷰미 프로젝트";
        String reviewRequestCode = "reviewRequestCode";
        reviewGroupRepository.save(new ReviewGroup(reviewee, projectName, reviewRequestCode, "groupAccessCode"));

        // when
        ReviewSetupResponse reviewCreationSetup = reviewService.findReviewCreationSetup(reviewRequestCode);

        // then
        assertAll(
                () -> assertThat(reviewCreationSetup.revieweeName()).isEqualTo(reviewee),
                () -> assertThat(reviewCreationSetup.projectName()).isEqualTo(projectName),
                () -> assertThat(reviewCreationSetup.questions()).hasSize(2),
                () -> assertThat(reviewCreationSetup.keywords()).hasSize(2)
        );
    }
}
