package reviewme.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ReceivedReviewsResponse(

        @Schema(description = "응답 크기")
        long size,

        @Schema(description = "마지막 리뷰 아이디")
        long lastReviewId,

        @Schema(description = "받은 리뷰 목록")
        List<ReceivedReviewResponse> reviews
) {
}
