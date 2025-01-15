package reviewme.config.client.exception;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.UnauthorizedException;

@Slf4j
public class GithubOauthFailedException extends UnauthorizedException {

    public GithubOauthFailedException() {
        super("깃허브 인증이 실패했어요.");
        log.info("Github oauth failed", this);
    }
}
