package reviewme.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reviewme.auth.service.AuthService;
import reviewme.auth.service.dto.GithubCodeRequest;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/v2/auth/github")
    public ResponseEntity<Void> authWithGithub(
            @Valid @RequestBody GithubCodeRequest request,
            HttpServletRequest httpRequest
    ) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/v2/auth/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest httpRequest
    ) {
        return ResponseEntity.noContent().build();
    }
}
