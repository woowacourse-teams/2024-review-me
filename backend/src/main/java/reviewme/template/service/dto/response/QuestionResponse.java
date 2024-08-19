package reviewme.template.service.dto.response;

import jakarta.annotation.Nullable;

public record QuestionResponse(
        long questionId,
        boolean required,
        String content,
        String questionType,
        @Nullable OptionGroupResponse optionGroup,
        boolean hasGuideline,
        @Nullable String guideline
) {
}
