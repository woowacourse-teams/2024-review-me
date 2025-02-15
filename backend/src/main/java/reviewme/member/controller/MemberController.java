package reviewme.member.controller;

import jakarta.servlet.http.HttpSession;
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
            HttpSession session
    ) {
        GitHubMember gitHubMember = sessionManager.getGitHubMember(session);
        ProfileResponse response = memberService.getProfile(gitHubMember);
        return ResponseEntity.ok(response);
    }
}
