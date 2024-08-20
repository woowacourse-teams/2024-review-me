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
            return formatHexadecimal(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new EncoderAlgorithmInitializationException(algorithm);
        }
    }

    private String formatHexadecimal(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append("%02x".formatted(b));
        }
        return builder.toString();
    }
}
