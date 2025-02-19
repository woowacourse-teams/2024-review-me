package reviewme.security.session;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.auth.domain.GitHubMember;
import reviewme.security.resolver.exception.GuestReviewGroupSessionNotExistsException;
import reviewme.security.resolver.exception.LoginMemberSessionNotExistsException;

@Service
@RequiredArgsConstructor
public class SessionManager {

    private static final String GITHUB_MEMBER_KEY = "githubMember";
    private static final String REVIEW_REQUEST_CODE_KEY = "reviewRequestCode";

    public void saveGitHubMember(HttpSession session, GitHubMember gitHubMember) {
        session.setAttribute(GITHUB_MEMBER_KEY, gitHubMember);
    }

    public GitHubMember getGitHubMember(HttpSession session) {
        if (session == null) {
            throw new LoginMemberSessionNotExistsException();
        }
        return (GitHubMember) session.getAttribute(GITHUB_MEMBER_KEY);
    }

    public void saveReviewRequestCode(HttpSession session, String reviewRequestCode) {
        session.setAttribute(REVIEW_REQUEST_CODE_KEY, reviewRequestCode);
    }

    public String getReviewRequestCode(HttpSession session) {
        if(session == null) {
            throw new GuestReviewGroupSessionNotExistsException();
        }
        return (String) session.getAttribute(REVIEW_REQUEST_CODE_KEY);
    }
}
