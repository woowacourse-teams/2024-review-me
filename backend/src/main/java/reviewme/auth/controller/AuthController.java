package reviewme.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reviewme.auth.domain.GitHubMember;
import reviewme.auth.service.AuthService;
import reviewme.security.session.SessionManager;
import reviewme.reviewgroup.service.dto.CheckValidAccessRequest;

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
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/v2/auth/group")
    public ResponseEntity<Void> authWithReviewGroup(
            @Valid @RequestBody CheckValidAccessRequest request,
            HttpSession session
    ) {
        String reviewRequestCode  = authService.authWithReviewGroup(request);
        sessionManager.saveReviewRequestCode(session, reviewRequestCode);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/v2/auth/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest httpRequest
    ) {
        return ResponseEntity.noContent().build();
    }
}
