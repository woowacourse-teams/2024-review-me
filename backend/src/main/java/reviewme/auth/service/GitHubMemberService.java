package reviewme.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.auth.domain.GitHubMember;
import reviewme.config.client.GitHubUserProperties;

@Service
@RequiredArgsConstructor
public class GitHubMemberService {

    private final GitHubUserProperties gitHubUserProperties;

    public GitHubMember createGitHubMember(long memberId, String gitHubNickname) {
        return new GitHubMember(
                memberId, gitHubNickname,
                String.format(gitHubUserProperties.profileUrlFormat(), gitHubNickname)
        );
    }
}
