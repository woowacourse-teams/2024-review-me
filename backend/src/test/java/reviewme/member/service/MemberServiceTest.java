package reviewme.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import reviewme.auth.domain.GitHubMember;
import reviewme.member.service.dto.ProfileResponse;
import reviewme.security.resolver.exception.LoginMemberSessionNotExistsException;

class MemberServiceTest {

    @Nested
    @DisplayName("사용자 프로필을 반환한다.")
    class getProfile {

        @Test
        void 세션에_저장된_정보가_없으면_예외가_발생한다() {
            // given
            MemberService memberService = new MemberService();

            // when, then
            assertThatCode(() -> memberService.getProfile(null))
                    .isInstanceOf(LoginMemberSessionNotExistsException.class);
        }

        @Test
        void 세션에_저장된_사용자_프로필을_반환한다() {
            // given
            long memberId = 1L;
            String nickname = "user";
            String profileImageUrl = "https://profile.com";
            GitHubMember gitHubMember = new GitHubMember(memberId, nickname, profileImageUrl);
            MemberService memberService = new MemberService();

            // when
            ProfileResponse profile = memberService.getProfile(gitHubMember);

            // then
            assertAll(
                    () -> assertThat(profile.memberId()).isEqualTo(memberId),
                    () -> assertThat(profile.nickname()).isEqualTo(nickname),
                    () -> assertThat(profile.profileImageUrl()).isEqualTo(profileImageUrl)
            );
        }
    }
}
