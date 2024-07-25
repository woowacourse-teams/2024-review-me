package reviewme.review.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static reviewme.fixture.KeywordFixture.꼼꼼하게_기록해요;
import static reviewme.fixture.KeywordFixture.의견을_잘_조율해요;
import static reviewme.fixture.MemberFixture.회원_산초;
import static reviewme.fixture.MemberFixture.회원_아루;
import static reviewme.fixture.MemberFixture.회원_커비;
import static reviewme.fixture.ReviewerGroupFixture.데드라인_남은_그룹;
import static reviewme.fixture.ReviewerGroupFixture.데드라인_지난_그룹;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import reviewme.keyword.domain.Keyword;
import reviewme.member.domain.GithubId;
import reviewme.member.domain.Member;
import reviewme.member.domain.ReviewerGroup;
import reviewme.review.domain.exception.DeadlineExpiredException;
import reviewme.review.domain.exception.IllegalReviewerException;
import reviewme.review.domain.exception.RevieweeMismatchException;
import reviewme.review.exception.GithubReviewerGroupUnAuthorizedException;
import reviewme.review.exception.ReviewAlreadySubmittedException;

class ReviewTest {

    @Test
    void 리뷰어와_리뷰이가_같을_수_없다() {
        // given
        Member member = 회원_산초.create();
        LocalDateTime createdAt = LocalDateTime.now();
        List<Keyword> keywords = List.of(꼼꼼하게_기록해요.create(), 의견을_잘_조율해요.create());

        // when, then
        assertThatThrownBy(() -> new Review(member, member, null, keywords, createdAt))
                .isInstanceOf(IllegalReviewerException.class);
    }

    @Test
    void 마감_기한이_지난_그룹에_리뷰를_등록할_수_없다() {
        // given
        Member reviewer = 회원_산초.create();
        Member reviewee = 회원_아루.create();
        ReviewerGroup reviewerGroup = 데드라인_지난_그룹.create(reviewee, List.of(reviewer.getGithubId()));
        LocalDateTime createdAt = LocalDateTime.now();
        List<Keyword> keywords = List.of();

        // when, then
        assertThatThrownBy(() -> new Review(reviewer, reviewee, reviewerGroup, keywords, createdAt))
                .isInstanceOf(DeadlineExpiredException.class);
    }

    @Test
    void 하나의_리뷰_그룹에_중복으로_리뷰를_등록할_수_없다() {
        // given
        Member reviewer = 회원_산초.create();
        Member reviewee = 회원_아루.create();
        ReviewerGroup reviewerGroup = 데드라인_남은_그룹.create(reviewee, List.of(reviewer.getGithubId()));
        new Review(reviewer, reviewee, reviewerGroup, List.of(), LocalDateTime.now());
        LocalDateTime createdAt = LocalDateTime.now();
        List<Keyword> keywords = List.of();

        // when, then
        assertThatThrownBy(() -> new Review(reviewer, reviewee, reviewerGroup, keywords, createdAt))
                .isInstanceOf(ReviewAlreadySubmittedException.class);
    }

    @Test
    void 리뷰어로_등록되지_않은_회원은_리뷰를_등록할_수_없다() {
        // given
        Member reviewer = 회원_산초.create();
        Member reviewee = 회원_아루.create();
        ReviewerGroup reviewerGroup = 데드라인_남은_그룹.create(reviewee, List.of(new GithubId(3)));
        LocalDateTime createdAt = LocalDateTime.now();
        List<Keyword> keywords = List.of();

        // when, then
        assertThatThrownBy(() -> new Review(reviewer, reviewee, reviewerGroup, keywords, createdAt))
                .isInstanceOf(GithubReviewerGroupUnAuthorizedException.class);
    }

    @Test
    void 그룹_내에서_그룹_밖으로_리뷰를_작성할_수_없다() {
        // given
        Member reviewer = 회원_산초.create();
        Member reviewee = 회원_아루.create();
        Member other = 회원_커비.create();
        ReviewerGroup reviewerGroup = 데드라인_남은_그룹.create(reviewee, List.of(reviewer.getGithubId()));
        LocalDateTime createdAt = LocalDateTime.now();
        List<Keyword> keywords = List.of();

        // when, then
        assertThatThrownBy(() -> new Review(reviewer, other, reviewerGroup, keywords, createdAt))
                .isInstanceOf(RevieweeMismatchException.class);
    }
}
