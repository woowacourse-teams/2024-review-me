package reviewme.util;

import lombok.extern.slf4j.Slf4j;
import reviewme.global.exception.ReviewMeException;

@Slf4j
public class EncoderAlgorithmInitializationException extends ReviewMeException {

    public EncoderAlgorithmInitializationException(String algorithm) {
        super("서버 내부에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
        log.error("Failed to initialize encoder: Algorithm not found: {}", algorithm, this);
    }
}
