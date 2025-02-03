package reviewme.global.session;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.auth.domain.GitHubMember;

@Service
@RequiredArgsConstructor
public class SessionManager {

    private static final String GITHUB_MEMBER_KEY = "githubMember";
    private static final String REVIEW_GROUP_KEY = "reviewRequestCode";

    public void saveGitHubMember(HttpSession session, GitHubMember gitHubMember) {
        session.setAttribute(GITHUB_MEMBER_KEY, gitHubMember);
    }

    public GitHubMember getGitHubMember(HttpSession session) {
        return (GitHubMember) session.getAttribute(GITHUB_MEMBER_KEY);
    }

    public void saveReviewGroup(HttpSession session, String reviewRequestCode) {
        session.setAttribute(REVIEW_GROUP_KEY, reviewRequestCode);
    }

    public String getReviewGroup(HttpSession session) {
        return (String) session.getAttribute(REVIEW_GROUP_KEY);
    }
}
