package reviewme.auth.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.auth.domain.MemberPrincipal;
import reviewme.auth.domain.Principal;
import reviewme.auth.service.dto.GithubCodeRequest;
import reviewme.config.client.GitHubOAuthClient;
import reviewme.config.client.dto.response.GitHubUserInfoResponse;
import reviewme.member.domain.Member;
import reviewme.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GitHubOAuthClient githubOAuthClient;
    private final MemberRepository memberRepository;

    @Transactional
    public Principal authWithGithub(@Valid GithubCodeRequest request) {
        GitHubUserInfoResponse userInfo = githubOAuthClient.getUserInfo(request.code());
        String email = userInfo.email();
        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(new Member(email)));
        return new MemberPrincipal(member);
    }
}
