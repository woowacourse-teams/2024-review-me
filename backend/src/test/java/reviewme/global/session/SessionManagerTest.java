package reviewme.global.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import reviewme.auth.domain.GitHubMember;
import reviewme.security.session.SessionManager;
import reviewme.support.ServiceTest;

@ServiceTest
class SessionManagerTest {

    @Autowired
    private SessionManager sessionManager;

    private MockHttpServletRequest request;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        session = new MockHttpSession();
        request.setSession(session);
    }

    @Test
    void 세션에_깃허브_멤버를_저장하고_조회한다() {
        // given
        GitHubMember gitHubMember = mock(GitHubMember.class);

        // when
        sessionManager.saveGitHubMember(request, gitHubMember);
        GitHubMember savedGitHubMember = sessionManager.getGitHubMember(request);

        // then
        assertThat(savedGitHubMember).isEqualTo(gitHubMember);
    }

    @Test
    void 세션이_없으면_깃허브_멤버를_조회할_때_null_을_반환한다() {
        // when
        request = new MockHttpServletRequest();
        GitHubMember gitHubMember = sessionManager.getGitHubMember(request);

        // then
        assertThat(gitHubMember).isNull();
    }

    @Test
    void 세션에_리뷰_그룹을_저장하고_조회한다() {
        // given
        String reviewRequestCode = "reviewRequestCode";

        // when
        sessionManager.saveReviewRequestCode(request, reviewRequestCode);
        String savedReviewGroup = sessionManager.getReviewRequestCode(request);

        // then
        assertThat(savedReviewGroup).isEqualTo(reviewRequestCode);
    }

    @Test
    void 세션이_없으면_리뷰_그룹을_조회할_때_null_을_반환한다() {
        // when
        request = new MockHttpServletRequest();
        String reviewGroup = sessionManager.getReviewRequestCode(request);

        // then
        assertThat(reviewGroup).isNull();
    }
}
