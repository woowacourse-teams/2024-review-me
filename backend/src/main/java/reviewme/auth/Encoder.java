package reviewme.auth;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Encoder {

    public String encode(String algorithm, String code) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] digest = messageDigest.digest(code.getBytes(UTF_8));
            return new String(digest, UTF_8);
        } catch (NoSuchAlgorithmException e) {
            throw new EncoderAlgorithmInitializationException(algorithm);
        }
    }
}
