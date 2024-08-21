package reviewme.review.service.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateReviewAnswerRequest(

        @NotNull(message = "질문 ID를 입력해주세요.")
        Long questionId,

        @Nullable
        List<Long> selectedOptionIds,

        @Nullable
        String text
) {
    public boolean isNotBlank() {
        return text != null && !text.isBlank();
    }
}
