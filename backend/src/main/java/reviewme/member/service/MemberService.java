package reviewme.member.service;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import reviewme.auth.domain.GitHubMember;
import reviewme.member.service.dto.ProfileResponse;
import reviewme.security.resolver.exception.LoginMemberSessionNotExistsException;

@Service
public class MemberService {

    public ProfileResponse getProfile(@Nullable GitHubMember gitHubMember) {
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
