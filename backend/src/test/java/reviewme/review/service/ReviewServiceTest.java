package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.KeywordFixture.꼼꼼하게_기록해요;
import static reviewme.fixture.KeywordFixture.추진력이_좋아요;
import static reviewme.fixture.KeywordFixture.회의를_이끌어요;
import static reviewme.fixture.QuestionFixure.기술역량이_어떤가요;
import static reviewme.fixture.QuestionFixure.소프트스킬이_어떤가요;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.keyword.domain.Keyword;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.question.domain.Question;
import reviewme.review.domain.Review;
import reviewme.review.domain.ReviewContent;
import reviewme.review.domain.ReviewKeyword;
import reviewme.review.domain.exception.ReviewGroupNotFoundByGroupAccessCodeException;
import reviewme.review.domain.exception.InvalidReviewAccessByReviewGroupException;
import reviewme.review.dto.request.CreateReviewContentRequest;
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.dto.response.QuestionSetupResponse;
import reviewme.review.dto.response.ReceivedReviewsResponse;
import reviewme.review.dto.response.ReviewDetailResponse;
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
        keywordRepository.save(추진력이_좋아요.create());
        keywordRepository.save(회의를_이끌어요.create());
        questionRepository.save(소프트스킬이_어떤가요.create());
        questionRepository.save(기술역량이_어떤가요.create());

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

    @Test
    void 확인_코드에_해당하는_그룹이_없는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> reviewService.findReceivedReviews("abc"))
                .isInstanceOf(ReviewGroupNotFoundByGroupAccessCodeException.class);
    }

    @Test
    void 확인_코드에_해당하는_그룹이_존재하면_리뷰_리스트를_반환한다() {
        // given
        String groupAccessCode = "5678";
        ReviewGroup reviewGroup = reviewGroupRepository.save(new ReviewGroup("산초", "리뷰미", "1234", groupAccessCode));
        Question question = questionRepository.save(기술역량이_어떤가요.create());
        Keyword keyword = keywordRepository.save(꼼꼼하게_기록해요.create());
        ReviewContent reviewContent1 = new ReviewContent(question.getId(), "기술역량 최고입니다 최고예요 !!!!!");
        ReviewContent reviewContent2 = new ReviewContent(question.getId(), "기술역량은 별로라고 생각해요 !!!!!");

        Review review1 = reviewRepository.save(
                new Review(reviewGroup.getId(), List.of(reviewContent1), LocalDateTime.now())
        );
        Review review2 = reviewRepository.save(
                new Review(reviewGroup.getId(), List.of(reviewContent2), LocalDateTime.now())
        );

        reviewKeywordRepository.saveAll(List.of(
                new ReviewKeyword(review1.getId(), keyword.getId()),
                new ReviewKeyword(review2.getId(), keyword.getId())
        ));

        // when
        ReceivedReviewsResponse response = reviewService.findReceivedReviews(groupAccessCode);

        // then
        assertThat(response.reviews()).hasSize(2);
    }

    @Test
    void 리뷰를_조회한다() {
        // given
        String groupAccessCode = "groupAccessCode";
        ReviewGroup reviewGroup = reviewGroupRepository.save(
                new ReviewGroup("테드", "리뷰미 프로젝트", "reviewRequestCode", groupAccessCode));
        Review review = reviewRepository.save(new Review(reviewGroup.getId(), List.of(), LocalDateTime.now()));

        // when
        ReviewDetailResponse response = reviewService.findReceivedReviewDetail(groupAccessCode,
                review.getId());

        // then
        assertThat(response.id()).isEqualTo(review.getId());
    }

    @Test
    void 잘못된_그룹_액세스_코드로_리뷰를_조회할_경우_예외를_발생한다() {
        // given
        ReviewGroup reviewGroup = reviewGroupRepository.save(
                new ReviewGroup("테드", "리뷰미 프로젝트", "reviewRequestCode", "groupAccessCode"));

        Review review = reviewRepository.save(new Review(reviewGroup.getId(), List.of(), LocalDateTime.now()));

        // when, then
        assertThatThrownBy(() -> reviewService.findReceivedReviewDetail("wrongGroupAccessCode", review.getId()))
                .isInstanceOf(ReviewGroupNotFoundByGroupAccessCodeException.class);
    }

    @Test
    void 리뷰_그룹에_해당하는_않는_리뷰를_조회할_경우_예외를_발생한다() {
        // given
        ReviewGroup reviewGroup1 = reviewGroupRepository.save(
                new ReviewGroup("테드", "리뷰미 프로젝트", "reviewRequestCode1", "groupAccessCode1"));
        ReviewGroup reviewGroup2 = reviewGroupRepository.save(
                new ReviewGroup("테드", "리뷰미 프로젝트", "reviewRequestCode2", "groupAccessCode2"));

        Review review1 = reviewRepository.save(new Review(reviewGroup1.getId(), List.of(), LocalDateTime.now()));
        Review review2 = reviewRepository.save(new Review(reviewGroup2.getId(), List.of(), LocalDateTime.now()));

        // when, then
        assertThatThrownBy(
                () -> reviewService.findReceivedReviewDetail(reviewGroup1.getGroupAccessCode(), review2.getId()))
                .isInstanceOf(InvalidReviewAccessByReviewGroupException.class);
    }

    @Test
    void 리뷰_질문_내용에서_특정_문자열을_리뷰이의_이름으로_치환한다() {
        // given
        reviewGroupRepository.save(
                new ReviewGroup("에프이", "리뷰미 프로젝트", "ABCD1234", "1234ABCD")
        );
        questionRepository.save(new Question("{revieweeName}에게 응원의 메시지를 전해주세요."));

        // when
        ReviewSetupResponse response = reviewService.findReviewCreationSetup("ABCD1234");
        QuestionSetupResponse questionResponse = response.questions().get(0);

        // then
        assertThat(questionResponse.content()).contains("에프이");
    }
}
