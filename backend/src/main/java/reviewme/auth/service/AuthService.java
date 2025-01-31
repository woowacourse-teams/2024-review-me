
package reviewme.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.auth.domain.GitHubMember;
import reviewme.auth.infrastructure.GitHubOAuthClient;
import reviewme.auth.infrastructure.dto.response.GitHubUserInfoResponse;
import reviewme.global.session.SessionManager;
import reviewme.member.domain.Member;
import reviewme.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GitHubOAuthClient githubOAuthClient;
    private final SessionManager sessionManager;
    private final GitHubMemberService gitHubMemberService;
    private final MemberRepository memberRepository;

    @Transactional
    public void authWithGithub(HttpServletRequest httpRequest, String code) {
        GitHubUserInfoResponse userInfo = githubOAuthClient.getUserInfo(code);
        Member member = getOrSaveMember(userInfo.email());
        GitHubMember gitHubMember = gitHubMemberService.createGitHubMember(userInfo.userName());

        HttpSession session = httpRequest.getSession();
        sessionManager.saveMember(session, member);
        sessionManager.saveGitHubMember(session, gitHubMember);
    }

    private Member getOrSaveMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(new Member(email)));
    }
}
