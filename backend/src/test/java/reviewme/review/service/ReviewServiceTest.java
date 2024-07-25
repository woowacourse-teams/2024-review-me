package reviewme.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.KeywordFixture.추진력이_좋아요;
import static reviewme.fixture.MemberFixture.회원_산초;
import static reviewme.fixture.MemberFixture.회원_아루;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.keyword.domain.Keyword;
import reviewme.keyword.repository.KeywordRepository;
import reviewme.member.domain.GithubId;
import reviewme.member.domain.Member;
import reviewme.member.domain.ReviewerGroup;
import reviewme.member.repository.MemberRepository;
import reviewme.member.repository.ReviewerGroupRepository;
import reviewme.review.domain.Question;
import reviewme.review.domain.Review;
import reviewme.review.dto.request.CreateReviewContentRequest;
import reviewme.review.dto.request.CreateReviewRequest;
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
}
