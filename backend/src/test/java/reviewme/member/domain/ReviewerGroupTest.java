package reviewme.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static reviewme.fixture.MemberFixture.회원_산초;
import static reviewme.fixture.MemberFixture.회원_아루;
import static reviewme.fixture.MemberFixture.회원_커비;
import static reviewme.fixture.ReviewerGroupFixture.데드라인_남은_그룹;
import static reviewme.fixture.ReviewerGroupFixture.데드라인_지난_그룹;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import reviewme.keyword.domain.Keyword;
import reviewme.member.domain.exception.DescriptionLengthExceededException;
import reviewme.member.domain.exception.EmptyReviewerException;
import reviewme.member.domain.exception.InvalidGroupNameLengthException;
import reviewme.member.domain.exception.SelfReviewException;
import reviewme.review.domain.Review;
import reviewme.review.domain.exception.DeadlineExpiredException;
import reviewme.review.domain.exception.RevieweeMismatchException;
import reviewme.review.exception.GithubReviewerGroupUnAuthorizedException;
import reviewme.review.exception.ReviewAlreadySubmittedException;

class ReviewerGroupTest {

    @Test
    void 리뷰_그룹이_올바르게_생성된다() {
        // given
        Member sancho = new Member("산초", 1);
        String groupName = "a".repeat(100);
        String description = "a".repeat(50);
        LocalDateTime createdAt = LocalDateTime.now();
        List<GithubId> githubIds = List.of(new GithubId(3));

        // when, then
        assertDoesNotThrow(
                () -> new ReviewerGroup(sancho, githubIds, groupName, description, createdAt));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 101})
    void 리뷰_그룹_이름_길이_제한을_벗어날_수_없다(int length) {
        // given
        String groupName = "a".repeat(length);
        Member sancho = 회원_산초.create();
        LocalDateTime createdAt = LocalDateTime.now();
        List<GithubId> githubIds = List.of();

        // when, then
        assertThatThrownBy(() -> new ReviewerGroup(sancho, githubIds, groupName, "설명", createdAt))
                .isInstanceOf(InvalidGroupNameLengthException.class);
    }

    @Test
    void 리뷰_그룹_설명_길이_제한을_벗어날_수_없다() {
        // given
        String description = "a".repeat(51);
        Member sancho = 회원_산초.create();
        LocalDateTime createdAt = LocalDateTime.now();
        List<GithubId> githubIds = List.of();

        // when, then
        assertThatThrownBy(() -> new ReviewerGroup(sancho, githubIds, "그룹 이름", description, createdAt))
                .isInstanceOf(DescriptionLengthExceededException.class);
    }

    @Test
    void 리뷰어_목록에_리뷰이가_들어갈_수_없다() {
        // given
        Member member = 회원_산초.create();
        String groupName = "Group";
        String description = "Description";
        LocalDateTime createdAt = LocalDateTime.now();
        List<GithubId> reviewerGithubIds = List.of(member.getGithubId());

        // when, then
        assertThatThrownBy(() -> new ReviewerGroup(member, reviewerGithubIds, groupName, description, createdAt))
                .isInstanceOf(SelfReviewException.class);
    }

    @Test
    void 리뷰어_목록이_비어있을_수_없다() {
        Member member = 회원_산초.create();
        String groupName = "Group";
        String description = "Description";
        LocalDateTime createdAt = LocalDateTime.now();
        List<GithubId> githubIds = List.of();

        // when, then
        assertThatThrownBy(() -> new ReviewerGroup(member, githubIds, groupName, description, createdAt))
                .isInstanceOf(EmptyReviewerException.class);
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
