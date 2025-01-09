package reviewme.template.service.dto.response;

import jakarta.annotation.Nullable;
import reviewme.template.domain.QuestionType;

public record QuestionResponse(
        long questionId,
        boolean required,
        String content,
        QuestionType questionType,
        @Nullable OptionGroupResponse optionGroup,
        boolean hasGuideline,
        @Nullable String guideline
) {
}
