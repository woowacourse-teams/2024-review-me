package reviewme.auth.domain;

import lombok.Getter;

@Getter
public class GitHubMember {

    private final long memberId;
    private final String gitHubUserName;
    private final String gitHubProfileImageUrl;

    public GitHubMember(long memberId, String gitHubUserName, String gitHubProfileImageUrl) {
        this.memberId = memberId;
        this.gitHubUserName = gitHubUserName;
        this.gitHubProfileImageUrl = gitHubProfileImageUrl;
    }
}
