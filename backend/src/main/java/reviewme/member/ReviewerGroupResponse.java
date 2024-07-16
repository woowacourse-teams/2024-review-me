package reviewme.member;

import java.time.LocalDateTime;

public record ReviewerGroupResponse(
        long id,
        String name,
        LocalDateTime deadline,
        MemberResponse reviewee
) {
}
