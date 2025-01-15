package reviewme.auth.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.auth.domain.MemberPrincipal;
import reviewme.auth.domain.Principal;
import reviewme.auth.service.dto.GithubCodeRequest;
import reviewme.config.client.GithubOAuthClient;
import reviewme.config.client.dto.response.GithubUserInfoResponse;
import reviewme.member.domain.Member;
import reviewme.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final GithubOAuthClient githubOAuthClient;
    private final MemberRepository memberRepository;

    public Principal authWithGithub(@Valid GithubCodeRequest request) {
        GithubUserInfoResponse userInfo = githubOAuthClient.getUserInfo(request.code());
        String email = userInfo.email();
        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(new Member(email)));
        return new MemberPrincipal(member);
    }
}
