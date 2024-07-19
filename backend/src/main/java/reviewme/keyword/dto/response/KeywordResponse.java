package reviewme.keyword.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "키워드 응답")
public record KeywordResponse(

        @Schema(description = "키워드 ID")
        long id,

        @Schema(description = "키워드명")
        String detail
) {
}
