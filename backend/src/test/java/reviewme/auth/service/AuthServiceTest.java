package reviewme.auth.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reviewme.auth.infrastructure.GitHubOAuthClient;
import reviewme.auth.infrastructure.dto.response.GitHubUserInfoResponse;
import reviewme.member.domain.Member;
import reviewme.member.repository.MemberRepository;
import reviewme.support.ServiceTest;

@ServiceTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @SpyBean
    private MemberRepository memberRepository;

    @MockBean
    private GitHubOAuthClient gitHubOAuthClient;

    @Nested
    class 깃허브를_통해_인증한다 {

        private final String authCode = "authCode";
        private final String gitHubUserName = "sancho";
        private final String gitHubEmail = "sancho@review-me.com";
        private final GitHubUserInfoResponse userInfo = new GitHubUserInfoResponse(gitHubUserName, gitHubEmail);

        @BeforeEach
        void setUp() {
            given(gitHubOAuthClient.getUserInfo(authCode)).willReturn(userInfo);
        }

        @Test
        void 가입하지_않은_회원이면_가입시킨다() {
            // when
            authService.authWithGithub(authCode);

            // then
            verify(memberRepository, times(1)).save(
                    argThat(member -> member.getEmail().equals(gitHubEmail))
            );
        }

        @Test
        void 가입된_회원이면_재가입하지_않는다() {
            // given
            memberRepository.save(new Member(gitHubEmail));
            clearInvocations(memberRepository);

            // when
            authService.authWithGithub(authCode);

            // then
            verify(memberRepository, never()).save(any());
        }
    }
}
