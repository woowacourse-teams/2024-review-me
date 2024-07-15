package reviewme.review;

import java.time.LocalDateTime;
import java.util.List;
import reviewme.member.MemberResponse;

public record ReviewResponse(
        Long id,
//        LocalDateTime createdAt,
        MemberResponse reviewer,
        ReviewerGroupResponse reviewerGroup,
        List<ContentResponse> contents,
        List<KeywordResponse> keywords
) {
}
