
package reviewme.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewme.auth.domain.GitHubMember;
import reviewme.auth.infrastructure.GitHubOAuthClient;
import reviewme.auth.infrastructure.dto.response.GitHubUserInfoResponse;
import reviewme.auth.service.dto.GitHubOAuthRequest;
import reviewme.auth.service.exception.ReviewGroupUnauthorizedException;
import reviewme.member.domain.Member;
import reviewme.member.repository.MemberRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.service.ReviewGroupService;
import reviewme.reviewgroup.service.dto.CheckValidAccessRequest;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GitHubOAuthClient githubOAuthClient;
    private final GitHubMemberService gitHubMemberService;
    private final ReviewGroupService reviewGroupService;
    private final MemberRepository memberRepository;

    @Transactional
    public GitHubMember authWithGitHub(GitHubOAuthRequest request) {
        GitHubUserInfoResponse userInfo = githubOAuthClient.getUserInfo(request.code());
        Member member = getOrSaveMember(userInfo.gitHubId());
        return gitHubMemberService.createGitHubMember(member.getId(), userInfo.gitHubNickname());
    }

    private Member getOrSaveMember(String externalId) {
        return memberRepository.findByExternalId(externalId)
                .orElseGet(() -> memberRepository.save(new Member(externalId)));
    }

    @Transactional(readOnly = true)
    public String authWithReviewGroup(CheckValidAccessRequest request) {
        ReviewGroup reviewGroup = reviewGroupService.getReviewGroupByReviewRequestCode(request.reviewRequestCode());
        if (!reviewGroup.matchesGroupAccessCode(request.groupAccessCode())) {
            throw new ReviewGroupUnauthorizedException(reviewGroup.getId());
        }
        return reviewGroup.getReviewRequestCode();
    }
}
