package reviewme.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewme.auth.domain.GitHubMember;
import reviewme.config.client.GitHubUserProperties;

@Service
@RequiredArgsConstructor
public class GitHubMemberService {

    private final GitHubUserProperties gitHubUserProperties;

    public GitHubMember createGitHubMember(long memberId, String gitHubUserName) {
        return new GitHubMember(
                memberId, gitHubUserName,
                String.format(gitHubUserProperties.profileUriFormat(), gitHubUserName)
        );
    }
}
