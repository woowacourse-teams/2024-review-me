package reviewme.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reviewme.auth.domain.GitHubMember;
import reviewme.auth.service.AuthService;
import reviewme.auth.service.dto.GitHubOAuthRequest;
import reviewme.member.service.MemberService;
import reviewme.member.service.dto.ProfileResponse;
import reviewme.reviewgroup.service.dto.CheckValidAccessRequest;
import reviewme.security.session.SessionManager;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final SessionManager sessionManager;
    private final AuthService authService;
    private final MemberService memberService;

    @PostMapping("/v2/auth/github")
    public ResponseEntity<ProfileResponse> authWithGitHub(
            @RequestBody GitHubOAuthRequest request,
            HttpServletRequest httpRequest
    ) {
        GitHubMember gitHubMember = authService.authWithGitHub(request);
        sessionManager.saveGitHubMember(httpRequest, gitHubMember);
        ProfileResponse response = memberService.getProfile(gitHubMember);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/v2/auth/group")
    public ResponseEntity<Void> authWithReviewGroup(
            @Valid @RequestBody CheckValidAccessRequest request,
            HttpServletRequest httpRequest
    ) {
        String reviewRequestCode = authService.authWithReviewGroup(request);
        sessionManager.saveReviewRequestCode(httpRequest, reviewRequestCode);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/v2/auth/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest httpRequest
    ) {
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", "")
                .path("/")
                .maxAge(0)
                .secure(true)
                .httpOnly(true)
                .build();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
