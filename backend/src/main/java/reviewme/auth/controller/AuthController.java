package reviewme.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reviewme.auth.domain.GitHubMember;
import reviewme.auth.service.AuthService;
import reviewme.global.session.SessionManager;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final SessionManager sessionManager;
    private final AuthService authService;

    @GetMapping("/v2/auth/github")
    public ResponseEntity<Void> authWithGithub(
            @RequestParam String code,
            HttpSession session
    ) {
        GitHubMember gitHubMember = authService.authWithGithub(code);
        sessionManager.saveGitHubMember(session, gitHubMember);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v2/auth/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest httpRequest
    ) {
        return ResponseEntity.noContent().build();
    }
}
