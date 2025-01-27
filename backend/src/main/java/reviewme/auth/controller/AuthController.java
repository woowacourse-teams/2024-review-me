package reviewme.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reviewme.auth.domain.Principal;
import reviewme.auth.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/v2/auth/github")
    public ResponseEntity<Void> authWithGithub(
            @RequestParam String code,
            HttpServletRequest httpRequest
    ) {
        Principal principal = authService.authWithGithub(code);
        HttpSession session = httpRequest.getSession();
        session.setAttribute("principal", principal);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v2/auth/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest httpRequest
    ) {
        return ResponseEntity.noContent().build();
    }
}
