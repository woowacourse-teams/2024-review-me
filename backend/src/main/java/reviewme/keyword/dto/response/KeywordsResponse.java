package reviewme.keyword.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "키워드 목록 응답")
public record KeywordsResponse(

        @Schema(description = "키워드 목록")
        List<KeywordResponse> keywords
) {
}
