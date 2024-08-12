package reviewme.review.dto.request.create;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "리뷰 생성 요청")
public record CreateReviewRequest2(

        @Schema(description = "리뷰 요청 코드")
        String reviewRequestCode,

        @Schema(description = "답변 목록")
        List<CreateReviewAnswerRequest> answers
) {
}
