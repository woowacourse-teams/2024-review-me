package reviewme.security.session;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.auth.domain.GitHubMember;

@Service
@RequiredArgsConstructor
public class SessionManager {

    private static final String GITHUB_MEMBER_KEY = "githubMember";
    private static final String REVIEW_REQUEST_CODE_KEY = "reviewRequestCode";

    public void saveGitHubMember(HttpServletRequest httpRequest, GitHubMember gitHubMember) {
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute(GITHUB_MEMBER_KEY, gitHubMember);
    }

    public @Nullable GitHubMember getGitHubMember(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return null;
        }
        return (GitHubMember) session.getAttribute(GITHUB_MEMBER_KEY);
    }

    public void saveReviewRequestCode(HttpServletRequest httpRequest, String reviewRequestCode) {
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute(REVIEW_REQUEST_CODE_KEY, reviewRequestCode);
    }

    public @Nullable String getReviewRequestCode(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return null;
        }
        return (String) session.getAttribute(REVIEW_REQUEST_CODE_KEY);
    }
}
