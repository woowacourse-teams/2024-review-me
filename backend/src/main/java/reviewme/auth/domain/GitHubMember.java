package reviewme.auth.domain;

import lombok.Getter;

@Getter
public class GitHubMember {

    private final String userName;
    private final String userImageUrl;

    public  GitHubMember(String userName, String userImageUrl) {
        this.userName = userName;
        this.userImageUrl = userImageUrl;
    }
}
