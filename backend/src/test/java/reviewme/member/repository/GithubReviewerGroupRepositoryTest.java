package reviewme.member.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static reviewme.fixture.ReviewerGroupFixture.리뷰_그룹;

import java.util.List;
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
    void 깃허브_아이디와_리뷰어_그룹이_모두_일치하는_깃허브_리뷰어_그룹이_있는_경우를_확인한다() { // given
        // given
        String revieweeGithubId = "kirby";
        Member reviewee = new Member("커비", revieweeGithubId);
        String reviewerGithubId = "ted";
        Member reviewer = new Member("테드", reviewerGithubId);
        memberRepository.saveAll(List.of(reviewee, reviewer));

        ReviewerGroup reviewerGroup = 리뷰_그룹.create(reviewee);
        reviewerGroupRepository.save(reviewerGroup);
        githubReviewerGroupRepository.save(new GithubReviewerGroup(reviewerGithubId, reviewerGroup));

        // when
        boolean actual = githubReviewerGroupRepository.existsByGithubIdAndReviewerGroup(reviewerGithubId, reviewerGroup);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 깃허브_아이디와_리뷰어_그룹이_모두_일치하는_깃허브_리뷰어_그룹이_없는_경우를_확인한다() {
        // given
        String revieweeGithubId = "kirby";
        Member reviewee = new Member("커비", revieweeGithubId);
        String reviewerGithubId = "ted";
        Member reviewer = new Member("테드", reviewerGithubId);
        memberRepository.saveAll(List.of(reviewee, reviewer));

        ReviewerGroup reviewerGroup = 리뷰_그룹.create(reviewee);
        reviewerGroupRepository.save(reviewerGroup);
        githubReviewerGroupRepository.save(new GithubReviewerGroup(reviewerGithubId, reviewerGroup));

        // when
        boolean actual = githubReviewerGroupRepository.existsByGithubIdAndReviewerGroup("aru", reviewerGroup);

        // then
        assertThat(actual).isFalse();
    }
}
