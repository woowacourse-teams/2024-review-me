package reviewme.member.service;

import org.springframework.stereotype.Service;
import reviewme.auth.domain.GitHubMember;
import reviewme.member.service.dto.ProfileResponse;
import reviewme.security.resolver.exception.LoginMemberSessionNotExistsException;

@Service
public class MemberService {

    public ProfileResponse getProfile(GitHubMember gitHubMember) {
        if (gitHubMember == null) {
            throw new LoginMemberSessionNotExistsException();
        }

        return new ProfileResponse(
                gitHubMember.getMemberId(),
                gitHubMember.getGitHubUserName(),
                gitHubMember.getGitHubProfileImageUrl()
        );
    }
}
