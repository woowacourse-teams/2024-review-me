package reviewme.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "내가 받은 리뷰 응답")
public record ReceivedReviewsResponse(

        @Schema(description = "응답 개수")
        long size,

        @Schema(description = "마지막 리뷰 아이디")
        long lastReviewId,

        @Schema(description = "받은 리뷰 목록")
        List<ReceivedReviewResponse> reviews
) {
}
