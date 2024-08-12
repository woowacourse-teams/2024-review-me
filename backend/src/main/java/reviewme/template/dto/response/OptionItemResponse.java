package reviewme.template.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "옵션 응답")
public record OptionItemResponse(

        @Schema(description = "옵션 ID")
        long optionId,

        @Schema(description = "내용")
        String content
) {
}
