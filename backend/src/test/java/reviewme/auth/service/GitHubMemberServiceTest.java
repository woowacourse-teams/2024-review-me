package reviewme.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reviewme.auth.domain.GitHubMember;
import reviewme.support.ServiceTest;

@ServiceTest
class GitHubMemberServiceTest {

    @Autowired
    private GitHubMemberService gitHubMemberService;

    @Test
    void 깃허브_멤버를_생성한다() {
        // when
        long memberId = 1L;
        String gitHubUserName = "yonso";
        GitHubMember gitHubMember = gitHubMemberService.createGitHubMember(memberId, gitHubUserName);

        // then
        assertAll(
                () -> assertThat(gitHubMember.getMemberId()).isEqualTo(memberId),
                () -> assertThat(gitHubMember.getGitHubUserName()).isEqualTo(gitHubUserName),
                () -> assertThat(gitHubMember.getGitHubProfileImageUrl()).contains(gitHubUserName)
        );
    }
}
