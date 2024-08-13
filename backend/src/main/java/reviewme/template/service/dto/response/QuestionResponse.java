package reviewme.template.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

@Schema(name = "질문 응답")
public record QuestionResponse(

        @Schema(description = "질문 ID")
        long questionId,

        @Schema(description = "필수 여부")
        boolean required,

        @Schema(description = "질문")
        String content,

        @Schema(description = "질문 유형", examples = {"CHECKBOX", "TEXT"})
        String questionType,

        @Schema(description = "옵션 그룹", nullable = true)
        @Nullable
        OptionGroupResponse optionGroup,

        @Schema(description = "가이드라인 제공 여부")
        boolean hasGuideline,

        @Schema(description = "가이드라인", nullable = true)
        @Nullable
        String guideline
) {
}
