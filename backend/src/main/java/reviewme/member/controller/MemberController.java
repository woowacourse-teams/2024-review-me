package reviewme.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reviewme.auth.domain.GitHubMember;
import reviewme.member.service.MemberService;
import reviewme.member.service.dto.ProfileResponse;
import reviewme.security.session.SessionManager;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final SessionManager sessionManager;

    @GetMapping("/v2/members/profile")
    public ResponseEntity<ProfileResponse> getProfile(
            HttpServletRequest request
    ) {
        GitHubMember gitHubMember = sessionManager.getGitHubMember(request);
        ProfileResponse response = memberService.getProfile(gitHubMember);
        return ResponseEntity.ok(response);
    }
}
