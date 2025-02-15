package reviewme.auth.service.dto;

import reviewme.auth.domain.GitHubMember;

public record GitHubOAuthResponse(
        long memberId,
        String nickname,
        String profileImageUrl
) {

    public GitHubOAuthResponse (GitHubMember gitHubMember) {
        this(gitHubMember.getMemberId(), gitHubMember.getGitHubUserName(), gitHubMember.getGitHubProfileImageUrl());
    }
}
