package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.keyword.domain.Keyword;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.member.domain.GithubReviewerGroup;
import reviewme.member.domain.Member;
import reviewme.member.domain.ReviewerGroup;
import reviewme.member.repository.GithubReviewerGroupRepository;
import reviewme.member.repository.MemberRepository;
import reviewme.member.repository.ReviewerGroupRepository;
import reviewme.review.domain.Review;
import reviewme.review.domain.exception.DeadlineExpiredException;
import reviewme.review.dto.request.CreateReviewContentRequest;
import reviewme.review.dto.request.CreateReviewRequest;
import reviewme.review.dto.response.ReviewResponse;
import reviewme.review.exception.GithubReviewerGroupUnAuthorizedException;
import reviewme.review.exception.ReviewAlreadySubmittedException;
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
    GithubReviewerGroupRepository githubReviewerGroupRepository;

    @Autowired
    KeywordRepository keywordRepository;

    @Autowired
    ReviewContentRepository reviewContentRepository;

    @Test
    void 리뷰를_작성한다() {
        // given
        memberRepository.save(new Member("산초", "sancho"));
        Member reviewee = memberRepository.save(new Member("아루", "aru"));
        ReviewerGroup reviewerGroup = reviewerGroupRepository.save(
                new ReviewerGroup(reviewee, "그룹A", "그룹 설명", LocalDateTime.now().minusDays(1))
        );
        githubReviewerGroupRepository.save(new GithubReviewerGroup("sancho", reviewerGroup));
        Keyword keyword1 = keywordRepository.save(new Keyword("꼼꼼해요"));
        Keyword keyword2 = keywordRepository.save(new Keyword("친절해요"));

        CreateReviewContentRequest contentRequest1 = new CreateReviewContentRequest(
                1L, "소프트스킬이 어떤가요?", "소통을 잘해요"
        );
        CreateReviewContentRequest contentRequest2 = new CreateReviewContentRequest(
                2L, "기술역량이 어떤가요?", "스트림을 잘다뤄요"
        );
        CreateReviewRequest createReviewRequest = new CreateReviewRequest(
                1L,
                1L,
                List.of(contentRequest1, contentRequest2),
                List.of(keyword1.getId(), keyword2.getId())
        );

        // when
        reviewService.createReview(createReviewRequest);

        // then
        List<Review> actual = reviewRepository.findAll();
        assertThat(actual).hasSize(1);
    }

    @Test
    void 리뷰를_조회한다() {
        // given
        Member reviewer = memberRepository.save(new Member("테드", "ted"));
        Member reviewee = memberRepository.save(new Member("아루", "aru"));
        memberRepository.save(new Member("산초", "sancho"));
        ReviewerGroup reviewerGroup = reviewerGroupRepository.save(new ReviewerGroup(
                reviewee,
                "그룹A",
                "그룹 설명",
                LocalDateTime.of(2024, 1, 1, 1, 1))
        );
        Review review = reviewRepository.save(new Review(reviewer, reviewerGroup, LocalDateTime.now()));

        // when
        ReviewResponse response = reviewService.findReview(review.getId());

        // then
        Long id = response.id();
        assertThat(id).isEqualTo(review.getId());
    }

    @Test
    void 리뷰어_그룹에_속하지_않는_리뷰어가_리뷰를_작성할_경우_예외를_발생한다() {
        // given
        Member reviewee = memberRepository.save(new Member("아루", "aru"));
        Member reviewer = memberRepository.save(new Member("테드", "ted"));
        ReviewerGroup reviewerGroup = reviewerGroupRepository.save(new ReviewerGroup(
                reviewee,
                "그룹A",
                "그룹 설명",
                LocalDateTime.of(2024, 1, 1, 1, 1))
        );
        githubReviewerGroupRepository.save(new GithubReviewerGroup("kirby", reviewerGroup));

        CreateReviewRequest createReviewRequest = new CreateReviewRequest(
                reviewer.getId(),
                reviewerGroup.getId(),
                List.of(),
                List.of()
        );

        // when, then
        assertThatThrownBy(() -> reviewService.createReview(createReviewRequest))
                .isInstanceOf(GithubReviewerGroupUnAuthorizedException.class);
    }

    @Test
    void 이미_작성한_리뷰가_있는데_리뷰를_작성할_경우_예외를_발생한다() {
        // given
        Member reviewee = memberRepository.save(new Member("아루", "aru"));
        Member reviewer = memberRepository.save(new Member("테드", "ted"));
        ReviewerGroup reviewerGroup = reviewerGroupRepository.save(new ReviewerGroup(
                reviewee,
                "그룹A",
                "그룹 설명",
                LocalDateTime.of(2024, 1, 1, 1, 1))
        );
        githubReviewerGroupRepository.save(new GithubReviewerGroup("ted", reviewerGroup));

        CreateReviewRequest createReviewRequest = new CreateReviewRequest(
                reviewer.getId(),
                reviewerGroup.getId(),
                List.of(),
                List.of()
        );

        reviewRepository.save(new Review(reviewer, reviewerGroup, LocalDateTime.now()));

        // when, then
        assertThatThrownBy(() -> reviewService.createReview(createReviewRequest))
                .isInstanceOf(ReviewAlreadySubmittedException.class);
    }

    @Test
    void 데드라인이_지난_리뷰그룹에_대해_리뷰를_작성하려하면_예외가_발생한다() {
        // given
        memberRepository.save(new Member("산초", "sancho"));
        Member reviewee = memberRepository.save(new Member("아루", "aru"));
        LocalDateTime createdAt = LocalDateTime.now().minusDays(7).minusMinutes(1);
        ReviewerGroup reviewerGroup = reviewerGroupRepository.save(
                new ReviewerGroup(reviewee, "그룹A", "그룹 설명", createdAt)
        );
        githubReviewerGroupRepository.save(new GithubReviewerGroup("sancho", reviewerGroup));
        CreateReviewRequest createReviewRequest = new CreateReviewRequest(
                1L, 1L, List.of(), List.of()
        );

        // when, then
        assertThatThrownBy(() -> reviewService.createReview(createReviewRequest))
                .isInstanceOf(DeadlineExpiredException.class);
    }
}
