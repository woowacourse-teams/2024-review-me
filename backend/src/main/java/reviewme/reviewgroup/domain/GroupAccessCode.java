package reviewme.reviewgroup.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewme.reviewgroup.domain.exception.InvalidGroupAccessCodeFormatException;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupAccessCode {

    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9]{4,20}$");

    @Column(name = "group_access_code", nullable = false)
    private String groupAccessCode;

    public GroupAccessCode(String groupAccessCode) {
        validateGroupAccessCode(groupAccessCode);
        this.groupAccessCode = groupAccessCode;
    }

    private void validateGroupAccessCode(String groupAccessCode) {
        Matcher matcher = PATTERN.matcher(groupAccessCode);
        if (!matcher.matches()) {
            throw new InvalidGroupAccessCodeFormatException(groupAccessCode);
        }
    }
}

