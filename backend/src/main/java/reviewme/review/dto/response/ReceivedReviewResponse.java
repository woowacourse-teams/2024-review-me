package reviewme.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "리뷰 내용 응답")
public record ReceivedReviewResponse(

        @Schema(description = "리뷰 아이디")
        long id,

        @Schema(description = "공개 여부")
        boolean isPublic,

        @Schema(description = "리뷰 작성일")
        LocalDate createdAt,

        @Schema(description = "응답 내용 미리보기")
        String contentPreview,

        @Schema(description = "리뷰어 그룹 정보")
        ReceivedReviewReviewerGroupResponse reviewerGroup,

        @Schema(description = "키워드")
        List<ReceivedReviewKeywordsResponse> keywords
) {
}
