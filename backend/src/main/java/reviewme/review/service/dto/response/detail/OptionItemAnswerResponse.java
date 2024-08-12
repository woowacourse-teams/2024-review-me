package reviewme.review.service.dto.response.detail;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "옵션 및 선택 정보")
public record OptionItemAnswerResponse(

        @Schema(description = "옵션 ID")
        long optionId,

        @Schema(description = "내용")
        String content,

        @Schema(description = "선택 여부")
        boolean isChecked
) {
}
