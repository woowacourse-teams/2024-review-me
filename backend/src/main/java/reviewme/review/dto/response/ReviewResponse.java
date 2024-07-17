package reviewme.review.dto.response;

import java.util.List;
import reviewme.keyword.dto.response.KeywordResponse;
import reviewme.member.dto.response.MemberResponse;
import reviewme.member.dto.response.ReviewerGroupResponse;

public record ReviewResponse(
        long id,
//        LocalDateTime createdAt,
        MemberResponse reviewer,
        ReviewerGroupResponse reviewerGroup,
        List<ReviewContentResponse> contents,
        List<KeywordResponse> keywords
) {
}
