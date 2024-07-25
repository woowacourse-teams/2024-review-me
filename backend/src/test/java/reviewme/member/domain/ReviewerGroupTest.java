package reviewme.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static reviewme.fixture.MemberFixture.회원_산초;
import static reviewme.fixture.MemberFixture.회원_커비;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import reviewme.member.domain.exception.DescriptionLengthExceededException;
import reviewme.member.domain.exception.DuplicateReviewerException;
import reviewme.member.domain.exception.EmptyReviewerException;
import reviewme.member.domain.exception.InvalidGroupNameLengthException;
import reviewme.member.domain.exception.SelfReviewException;

class ReviewerGroupTest {

    @Test
    void 리뷰_그룹이_올바르게_생성된다() {
        // given
        Member member = 회원_산초.create();
        String groupName = "a".repeat(100);
        String description = "a".repeat(50);
        LocalDateTime createdAt = LocalDateTime.now();
        List<GithubId> githubIds = List.of(new GithubId(3));

        // when, then
        assertDoesNotThrow(
                () -> new ReviewerGroup(member, githubIds, groupName, description, createdAt));
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
    void 리뷰어를_중복으로_추가할_수_없다() {
        // given
        Member reviewee = 회원_산초.create();
        Member reviewer = 회원_커비.create();

        String groupName = "Group";
        String description = "Description";
        LocalDateTime createdAt = LocalDateTime.now();
        List<GithubId> githubIds = List.of(reviewer.getGithubId());
        ReviewerGroup reviewerGroup = new ReviewerGroup(reviewee, githubIds, groupName, description, createdAt);
        GithubIdReviewerGroup githubIdReviewerGroup = new GithubIdReviewerGroup(reviewee.getGithubId(), reviewerGroup);
        // when, then
        assertThatThrownBy(() -> reviewerGroup.addReviewerGithubId(githubIdReviewerGroup))
                .isInstanceOf(DuplicateReviewerException.class);
    }

}
