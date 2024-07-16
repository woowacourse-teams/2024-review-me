package reviewme.review;

import java.util.List;
import reviewme.keyword.KeywordResponse;
import reviewme.member.MemberResponse;

public record ReviewResponse(
        long id,
//        LocalDateTime createdAt,
        MemberResponse reviewer,
        ReviewerGroupResponse reviewerGroup,
        List<ContentResponse> contents,
        List<KeywordResponse> keywords
) {
}
