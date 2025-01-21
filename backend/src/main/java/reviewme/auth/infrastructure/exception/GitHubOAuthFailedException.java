package reviewme.auth.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import reviewme.global.exception.UnauthorizedException;

@Slf4j
public class GitHubOAuthFailedException extends UnauthorizedException {

    public GitHubOAuthFailedException(HttpStatusCode statusCode, String message) {
        super("깃허브 인증에 실패했어요.");
        log.warn("GitHub OAuth failed - GitHub's response code: {}, message: {}", statusCode, message, this);
    }
}
