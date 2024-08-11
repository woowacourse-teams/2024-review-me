package reviewme.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "선택된 카테고리 응답")
public record ReceivedReviewCategoryResponse(

        @Schema(description = "카테고리 ID")
        long optionId,

        @Schema(description = "카테고리 내용")
        String content
) {
}
