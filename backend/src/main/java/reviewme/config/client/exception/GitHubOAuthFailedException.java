package reviewme.config.client.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.UnauthorizedException;

@Slf4j
public class GitHubOAuthFailedException extends UnauthorizedException {

    public GitHubOAuthFailedException() {
        super("깃허브 인증이 실패했어요.");
        log.info("GitHub OAuth failed", this);
    }
}
