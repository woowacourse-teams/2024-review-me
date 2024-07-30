package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.KeywordFixture.추진력이_좋아요;
import static reviewme.fixture.KeywordFixture.회의를_이끌어요;
import static reviewme.fixture.MemberFixture.회원_산초;
import static reviewme.fixture.MemberFixture.회원_아루;
import static reviewme.fixture.MemberFixture.회원_커비;
import static reviewme.fixture.MemberFixture.회원_테드;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.keyword.domain.Keyword;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.member.repository.MemberRepository;
import reviewme.member.repository.ReviewerGroupRepository;
import reviewme.question.domain.Question;
import reviewme.review.domain.Review;
import reviewme.review.domain.ReviewContent;
import reviewme.review.dto.request.CreateReviewContentRequest;
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.dto.response.ReceivedReviewsResponse;
import reviewme.review.repository.QuestionRepository;
import reviewme.review.repository.ReviewContentRepository;
import reviewme.review.repository.ReviewRepository;
import reviewme.support.ServiceTest;

@ServiceTest
class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReviewerGroupRepository reviewerGroupRepository;

    @Autowired
    KeywordRepository keywordRepository;

    @Autowired
    ReviewContentRepository reviewContentRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Test
    void 리뷰를_작성한다() {
        // given
        Member reviewer = memberRepository.save(회원_산초.create());
        Member reviewee = memberRepository.save(회원_아루.create());
        List<GithubId> reviewerGithubIds = List.of(reviewer.getGithubId());

        ReviewerGroup reviewerGroup = reviewerGroupRepository.save(
                new ReviewerGroup(reviewee, reviewerGithubIds, "그룹명", "그룹설명", LocalDateTime.now().plusDays(1))
        );
        Question question = questionRepository.save(new Question("질문"));
        Keyword keyword = keywordRepository.save(추진력이_좋아요.create());
        CreateReviewContentRequest contentRequest = new CreateReviewContentRequest(question.getId(), "답변".repeat(10));

        CreateReviewRequest createReviewRequest = new CreateReviewRequest(reviewer.getId(), reviewerGroup.getId(),
                List.of(contentRequest), List.of(keyword.getId())
        );

        // when
        reviewService.createReview(createReviewRequest);

        // then
        List<Review> actual = reviewRepository.findAll();
        assertThat(actual).hasSize(1);
    }

    @Test
    void 내가_받은_리뷰를_조회한다() {
        // given
        Member reviewee = memberRepository.save(회원_아루.create());
        Member reviewerSancho = memberRepository.save(회원_산초.create());
        Member reviewerKirby = memberRepository.save(회원_커비.create());
        Member reviewerTed = memberRepository.save(회원_테드.create());
        Keyword keyword1 = keywordRepository.save(추진력이_좋아요.create());
        Keyword keyword2 = keywordRepository.save(회의를_이끌어요.create());
        ReviewerGroup reviewerGroup = reviewerGroupRepository.save(new ReviewerGroup(
                reviewee,
                List.of(reviewerSancho.getGithubId(),
                        reviewerKirby.getGithubId(),
                        reviewerTed.getGithubId()),
                "빼깬드그룹",
                "빼깬드그룹 설명",
                LocalDateTime.now().plusDays(3)
        ));
        Question question = questionRepository.save(new Question("질문"));

        Review sanchoReview = reviewRepository.save(
                new Review(reviewerSancho, reviewee, reviewerGroup, List.of(keyword1), LocalDateTime.now().minusDays(1))
        );
        Review kirbyReview = reviewRepository.save(
                new Review(reviewerKirby, reviewee, reviewerGroup, List.of(keyword2), LocalDateTime.now())
        );
        Review tedReview = reviewRepository.save(
                new Review(reviewerTed, reviewee, reviewerGroup, List.of(keyword1, keyword2),
                        LocalDateTime.now().plusDays(1))
        );
        reviewContentRepository.saveAll(List.of(
                new ReviewContent(sanchoReview, question, "산초의 답변".repeat(50)),
                new ReviewContent(kirbyReview, question, "커비의 답변".repeat(50)),
                new ReviewContent(tedReview, question, "테드의 답변".repeat(50)))
        );

        // when
        ReceivedReviewsResponse 가장_최근에_받은_리뷰_조회
                = reviewService.findMyReceivedReview(reviewee.getId(), null, 2);
        ReceivedReviewsResponse 특정_리뷰_이전_리뷰_조회
                = reviewService.findMyReceivedReview(reviewee.getId(), 2L, 2);

        // then
        assertAll(
                () -> assertThat(가장_최근에_받은_리뷰_조회.reviews())
                        .hasSize(2),
                () -> assertThat(가장_최근에_받은_리뷰_조회.reviews().get(0).id())
                        .isEqualTo(tedReview.getId()),
                () -> assertThat(가장_최근에_받은_리뷰_조회.reviews().get(1).id())
                        .isEqualTo(kirbyReview.getId()),
                () -> assertThat(가장_최근에_받은_리뷰_조회.reviews().get(0).contentPreview().length())
                        .isLessThanOrEqualTo(150),

                () -> assertThat(특정_리뷰_이전_리뷰_조회.reviews())
                        .hasSize(1),
                () -> assertThat(특정_리뷰_이전_리뷰_조회.reviews().get(0).id())
                        .isEqualTo(sanchoReview.getId()),
                () -> assertThat(특정_리뷰_이전_리뷰_조회.reviews().get(0).contentPreview().length())
                        .isLessThanOrEqualTo(150)
        );
    }
}
