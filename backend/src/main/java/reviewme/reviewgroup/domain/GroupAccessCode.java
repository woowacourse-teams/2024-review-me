package reviewme.reviewgroup.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewme.reviewgroup.domain.exception.InvalidGroupAccessCodeFormatException;
import reviewme.util.Encoder;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupAccessCode {

    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9]{4,20}$");

    @Column(name = "group_access_code", nullable = false)
    private String code;

    public GroupAccessCode(String code) {
        validateGroupAccessCode(code);
        this.code = Encoder.encode(code);
    }

    private void validateGroupAccessCode(String groupAccessCode) {
        Matcher matcher = PATTERN.matcher(groupAccessCode);
        if (!matcher.matches()) {
            throw new InvalidGroupAccessCodeFormatException(groupAccessCode);
        }
    }

    public boolean matches(String groupAccessCode) {
        return code.equals(Encoder.encode(groupAccessCode));
    }
}
