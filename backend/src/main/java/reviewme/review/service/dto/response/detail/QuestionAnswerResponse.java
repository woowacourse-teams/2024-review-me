package reviewme.review.service.dto.response.detail;

import jakarta.annotation.Nullable;
import reviewme.template.domain.QuestionType;

public record QuestionAnswerResponse(
        long questionId,
        boolean required,
        QuestionType questionType,
        String content,
        @Nullable OptionGroupAnswerResponse optionGroup,
        @Nullable String answer
) {
}
