package reviewme.member.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.ReviewerGroupFixture.리뷰_그룹;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reviewme.member.domain.GithubReviewerGroup;
import reviewme.member.domain.Member;
import reviewme.member.domain.ReviewerGroup;

@DataJpaTest
class GithubReviewerGroupRepositoryTest {

    @Autowired
    GithubReviewerGroupRepository githubReviewerGroupRepository;

    @Autowired
    ReviewerGroupRepository reviewerGroupRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 깃허브_아이디와_리뷰어_그룹이_모두_일치하는_깃허브_리뷰어_그룹이_있는_경우를_확인한다() {
        // given
        String githubId = "ted";
        Member ted = new Member("테드", githubId);
        memberRepository.save(ted);
        ReviewerGroup reviewerGroup = 리뷰_그룹.create(ted);
        reviewerGroupRepository.save(reviewerGroup);
        githubReviewerGroupRepository.save(new GithubReviewerGroup(githubId, reviewerGroup));

        // when & then
        boolean actual = githubReviewerGroupRepository.existsByGithubIdAndReviewerGroup(githubId, reviewerGroup);
        assertThat(actual).isTrue();
    }

    @Test
    void 깃허브_아이디와_리뷰어_그룹이_모두_일치하는_깃허브_리뷰어_그룹이_없는_경우를_확인한다() {
        // given
        String githubId = "ted";
        Member ted = new Member("테드", githubId);
        memberRepository.save(ted);
        ReviewerGroup reviewerGroup = 리뷰_그룹.create(ted);
        reviewerGroupRepository.save(reviewerGroup);
        githubReviewerGroupRepository.save(new GithubReviewerGroup(githubId, reviewerGroup));

        // when & then
        boolean actual = githubReviewerGroupRepository.existsByGithubIdAndReviewerGroup("aru", reviewerGroup);
        assertThat(actual).isFalse();
    }
}
