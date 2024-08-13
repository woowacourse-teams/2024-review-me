package reviewme.template.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.util.List;

@Schema(name = "섹션 응답")
public record SectionResponse(

        @Schema(description = "섹션 ID")
        long sectionId,

        @Schema(description = "노출 조건")
        String visible,

        @Schema(description = "선택된 옵션 ID", nullable = true)
        @Nullable
        Long onSelectedOptionId,

        @Schema(description = "말머리")
        String header,

        @Schema(description = "질문 목록")
        List<QuestionResponse> questions
) {
}
