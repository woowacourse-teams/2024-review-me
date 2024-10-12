package reviewme.review.service.dto.response.detail;

import jakarta.annotation.Nullable;
import java.util.List;
import reviewme.question.domain.QuestionType;

public record QuestionAnswerResponse(
        long questionId,
        boolean required,
        QuestionType questionType,
        String content,
        @Nullable List<OptionItemAnswerResponse> options,
        @Nullable String answer
) {
}
