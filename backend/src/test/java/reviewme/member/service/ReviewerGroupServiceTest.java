package reviewme.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static reviewme.fixture.ReviewerGroupFixture.리뷰_그룹;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.member.domain.GithubId;
import reviewme.member.domain.Member;
import reviewme.member.domain.ReviewerGroup;
import reviewme.member.dto.response.ReviewCreationReviewerGroupResponse;
import reviewme.member.repository.MemberRepository;
import reviewme.member.repository.ReviewerGroupRepository;
import reviewme.support.ServiceTest;

@ServiceTest
class ReviewerGroupServiceTest {

    @Autowired
    ReviewerGroupService reviewerGroupService;

    @Autowired
    ReviewerGroupRepository reviewerGroupRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 리뷰_생성_시_필요한_리뷰어_그룹_정보를_조회한다() {
        // given
        Member reviewee = memberRepository.save(new Member("산초", 1));
        List<GithubId> reviewergithubIds = List.of(new GithubId(2), new GithubId(3));
        ReviewerGroup reviewerGroup = reviewerGroupRepository.save(리뷰_그룹.create(reviewee, reviewergithubIds));

        // when
        ReviewCreationReviewerGroupResponse actual = reviewerGroupService.findReviewCreationReviewerGroup(
                reviewerGroup.getId());

        // then
        assertAll(
                () -> assertThat(actual.id()).isEqualTo(reviewerGroup.getId()),
                () -> assertThat(actual.reviewee().id()).isEqualTo(reviewee.getId())
        );
    }
}
