package reviewme.review.service.dto.response.detail;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import reviewme.question.domain.QuestionType;

@Schema(name = "질문 및 답변 정보")
public record QuestionAnswerResponse(

        @Schema(description = "질문 ID")
        long questionId,

        @Schema(description = "필수 여부")
        boolean required,

        @Schema(description = "질문 유형")
        QuestionType questionType,

        @Schema(description = "질문")
        String content,

        @Schema(description = "가이드라인 제공 여부")
        boolean hasGuideline,

        @Schema(description = "가이드라인", nullable = true)
        @Nullable
        String guideline,

        @Schema(description = "옵션 그룹", nullable = true)
        @Nullable
        OptionGroupAnswerResponse optionGroup,

        @Schema(description = "답변", nullable = true)
        @Nullable
        String answer
) {
}
