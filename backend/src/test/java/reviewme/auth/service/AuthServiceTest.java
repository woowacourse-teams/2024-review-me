package reviewme.auth.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reviewme.auth.infrastructure.GitHubOAuthClient;
import reviewme.auth.infrastructure.dto.response.GitHubUserInfoResponse;
import reviewme.auth.service.dto.GitHubOAuthRequest;
import reviewme.auth.service.exception.ReviewGroupUnauthorizedException;
import reviewme.member.domain.Member;
import reviewme.member.repository.MemberRepository;
import reviewme.reviewgroup.domain.ReviewGroup;
import reviewme.reviewgroup.service.ReviewGroupService;
import reviewme.reviewgroup.service.dto.CheckValidAccessRequest;
import reviewme.support.ServiceTest;

@ServiceTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @SpyBean
    private MemberRepository memberRepository;

    @MockBean
    private ReviewGroupService reviewGroupService;

    @MockBean
    private GitHubOAuthClient gitHubOAuthClient;

    @Nested
    class 깃허브를_통해_인증한다 {

        private final String authCode = "authCode";
        private final String gitHubUserName = "sancho";
        private final String gitHubId = "12341234";
        private final GitHubUserInfoResponse userInfo = new GitHubUserInfoResponse(gitHubUserName, gitHubId);

        @BeforeEach
        void setUp() {
            given(gitHubOAuthClient.getUserInfo(authCode)).willReturn(userInfo);
        }

        @Test
        void 가입하지_않은_회원이면_가입시킨다() {
            // when
            GitHubOAuthRequest request = new GitHubOAuthRequest(authCode);
            authService.authWithGitHub(request);

            // then
            verify(memberRepository, times(1)).save(
                    argThat(member -> member.getExternalId().equals(gitHubId))
            );
        }

        @Test
        void 가입된_회원이면_재가입하지_않는다() {
            // given
            memberRepository.save(new Member(gitHubId));
            clearInvocations(memberRepository);
            GitHubOAuthRequest request = new GitHubOAuthRequest(authCode);

            // when
            authService.authWithGitHub(request);

            // then
            verify(memberRepository, never()).save(any());
        }
    }

    @Nested
    class 리뷰_그룹의_액세스_코드를_통해_인증한다 {

        private final String reviewRequestCode = "reviewRequestCode";
        private final String groupAccessCode = "groupAccessCode";

        @Test
        void 올바른_액세스_코드라면_예외가_발생하지_않는다() {
            // given
            ReviewGroup reviewGroup = Mockito.mock(ReviewGroup.class);
            given(reviewGroup.matchesGroupAccessCode(groupAccessCode)).willReturn(true);
            given(reviewGroup.getReviewRequestCode()).willReturn(reviewRequestCode);

            given(reviewGroupService.getReviewGroupByReviewRequestCode(reviewRequestCode))
                    .willReturn(reviewGroup);

            CheckValidAccessRequest request = new CheckValidAccessRequest(reviewRequestCode, groupAccessCode);

            // when & then
            assertDoesNotThrow(() -> authService.authWithReviewGroup(request));
        }

        @Test
        void 잘못된_액세스_코드라면_예외가_발생한다() {
            // given
            ReviewGroup reviewGroup = Mockito.mock(ReviewGroup.class);
            given(reviewGroup.getId()).willReturn(1L);

            given(reviewGroupService.getReviewGroupByReviewRequestCode(reviewRequestCode))
                    .willReturn(reviewGroup);

            CheckValidAccessRequest wrongRequest = new CheckValidAccessRequest(reviewRequestCode, "wrongCode");

            // when & then
            assertThatThrownBy(() -> authService.authWithReviewGroup(wrongRequest))
                    .isInstanceOf(ReviewGroupUnauthorizedException.class);
        }
    }
}
