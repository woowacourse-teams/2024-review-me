package reviewme.global.session;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.auth.domain.GitHubMember;
import reviewme.member.domain.Member;
import reviewme.reviewgroup.domain.ReviewGroup;

@Service
@RequiredArgsConstructor
public class SessionManager {

    private static final String MEMBER_KEY = "member";
    private static final String GITHUB_USER_KEY = "githubUser";
    private static final String REVIEW_GROUP_KEY = "reviewRequestCode";

    public void saveMember(HttpSession session, Object object) {
        session.setAttribute(MEMBER_KEY, object);
    }

    public Member getMember(HttpSession session) {
        try {
            return (Member) session.getAttribute(MEMBER_KEY);
        } catch (Exception e) {
            return null;
        }
    }

    public void saveGitHubMember(HttpSession session, GitHubMember gitHubMember) {
        session.setAttribute(GITHUB_USER_KEY, gitHubMember);
    }

    public GitHubMember getGitHubMember(HttpSession session) {
        try {
            return (GitHubMember) session.getAttribute(GITHUB_USER_KEY);
        } catch (Exception e) {
            return null;
        }
    }

    public void saveReviewGroup(HttpSession session, String reviewRequestCode) {
        session.setAttribute(REVIEW_GROUP_KEY, reviewRequestCode);
    }

    public ReviewGroup getReviewGroup(HttpSession session) {
        try {
            return (ReviewGroup) session.getAttribute(REVIEW_GROUP_KEY);
        } catch (Exception e) {
            return null;
        }
    }
}
