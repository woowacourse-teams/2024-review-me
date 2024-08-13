package reviewme.review.service.dto.response.list;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "내가 받은 리뷰 목록 응답")
public record ReceivedReviewsResponse(

        @Schema(description = "리뷰이 이름")
        String revieweeName,

        @Schema(description = "프로젝트 이름")
        String projectName,

        @Schema(description = "받은 리뷰 미리보기 목록")
        List<ReceivedReviewResponse> reviews
) {
}
