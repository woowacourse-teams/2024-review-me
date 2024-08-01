package reviewme.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "선택된 키워드 응답")
public record ReceivedReviewKeywordsResponse(

        @Schema(description = "키워드 아이디")
        long id,

        @Schema(description = "키워드 내용")
        String content
) {
}
