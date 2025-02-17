package reviewme.global.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import reviewme.auth.domain.GitHubMember;
import reviewme.security.session.SessionManager;
import reviewme.support.ServiceTest;

@ServiceTest
class SessionManagerTest {

    @Autowired
    private SessionManager sessionManager;

    private HttpSession session;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
    }

    @Test
    void 세션에_깃허브_멤버를_저장하고_조회한다() {
        // given
        GitHubMember gitHubMember = mock(GitHubMember.class);

        // when
        sessionManager.saveGitHubMember(session, gitHubMember);
        GitHubMember savedGitHubMember = sessionManager.getGitHubMember(session);

        // then
        assertThat(savedGitHubMember).isEqualTo(gitHubMember);
    }

    @Test
    void 세션에_리뷰_그룹을_저장하고_조회한다() {
        // given
        String reviewRequestCode = "reviewRequestCode";

        // when
        sessionManager.saveReviewRequestCode(session, reviewRequestCode);
        String savedReviewGroup = sessionManager.getReviewRequestCode(session);

        // then
        assertThat(savedReviewGroup).isEqualTo(reviewRequestCode);
    }
}
