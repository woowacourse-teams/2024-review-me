
package reviewme.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.auth.domain.GitHubMember;
import reviewme.auth.infrastructure.GitHubOAuthClient;
import reviewme.auth.infrastructure.dto.response.GitHubUserInfoResponse;
import reviewme.member.domain.Member;
import reviewme.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GitHubOAuthClient githubOAuthClient;
    private final GitHubMemberService gitHubMemberService;
    private final MemberRepository memberRepository;

    @Transactional
    public GitHubMember authWithGithub(String code) {
        GitHubUserInfoResponse userInfo = githubOAuthClient.getUserInfo(code);
        Member member = getOrSaveMember(userInfo.gitHubEmail());
        return gitHubMemberService.createGitHubMember(member.getId(), userInfo.gitHubNickname());
    }

    private Member getOrSaveMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(new Member(email)));
    }
}
