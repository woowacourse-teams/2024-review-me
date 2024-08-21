package reviewme.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoder {

    private static final String SHA_256 = "SHA-256";

    private Encoder() {
    }

    public static String encode(String code) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA_256);
            byte[] digest = messageDigest.digest(code.getBytes(UTF_8));
            return formatHexadecimal(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new EncoderAlgorithmInitializationException(SHA_256);
        }
    }

    private static String formatHexadecimal(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append("%02x".formatted(b));
        }
        return builder.toString();
    }
}
