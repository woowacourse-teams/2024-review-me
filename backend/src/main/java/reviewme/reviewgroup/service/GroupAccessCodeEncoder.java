package reviewme.reviewgroup.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reviewme.auth.Encoder;
import reviewme.reviewgroup.domain.exception.InvalidGroupAccessCodeFormatException;

@Component
@RequiredArgsConstructor
public class GroupAccessCodeEncoder {

    private static final Pattern PLAIN_CODE_PATTERN = Pattern.compile("^[a-zA-Z0-9]{4,20}$");
    private static final String SHA_256 = "SHA-256";

    private final Encoder encoder;

    public String encode(String groupAccessCode) {
        validateGroupAccessCode(groupAccessCode);
        return encoder.encode(SHA_256, groupAccessCode);
    }

    private void validateGroupAccessCode(String groupAccessCode) {
        Matcher matcher = PLAIN_CODE_PATTERN.matcher(groupAccessCode);
        if (!matcher.matches()) {
            throw new InvalidGroupAccessCodeFormatException(groupAccessCode);
        }
    }
}
